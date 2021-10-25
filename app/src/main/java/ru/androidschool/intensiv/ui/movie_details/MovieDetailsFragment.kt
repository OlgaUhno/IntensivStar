package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_detail_bottom_view.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDetailsDto
import ru.androidschool.intensiv.data.MovieDetailsVo
import ru.androidschool.intensiv.database.FavoriteMoviesDatabase
import ru.androidschool.intensiv.database.entities.FavoriteMovie
import ru.androidschool.intensiv.database.FavoriteMovieDao
import ru.androidschool.intensiv.network.MovieDetailsApiClient
import ru.androidschool.intensiv.rx.addCompletableSchedulers
import ru.androidschool.intensiv.ui.loadImage
import ru.androidschool.intensiv.util.MovieDetailsConverter
import ru.androidschool.intensiv.rx.addSingleSchedulers
import ru.androidschool.intensiv.util.Constants
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private lateinit var compositeDisposable: CompositeDisposable
    private var movieId: Int = -1
    private var posterPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(Constants.KEY_ID)
            Timber.i("show details for $movieId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()

        back_button.setOnClickListener { it ->
            activity?.supportFragmentManager?.popBackStack()
        }
        watch_button.setOnClickListener { it ->
            // TODO: implement logic
        }

        Timber.i("getMovieDetails for $movieId")

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            MovieDetailsApiClient.apiClient.getMovieDetails(movieId)
                .compose(addSingleSchedulers())
                .map { dto: MovieDetailsDto? -> MovieDetailsConverter.toViewObject(dto) }
                .subscribe({ response ->
                    addResult(
                        response
                    )
                }, { error -> Timber.e(error, "Failed get movie details") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun addResult(details: MovieDetailsVo) {
        poster_image.loadImage(details.posterPath)
        posterPath = details.posterPath
        release.text = details.release
        description.text = details.overview
        movie_rating.rating = details.rating
        movie_name.text = details.title
        production.text = details.productionCompanies
        genres.text = details.genres

        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(requireContext()).movieDao()

        db.isMovieExist(movieId).compose(addSingleSchedulers()).subscribe({
            setFavoriteState(it)
        }, { error -> Timber.e(error, "Failed get movie from database") })

        favorite.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                db.save(
                    FavoriteMovie(
                        movieId,
                        posterPath
                    )
                ).compose(addCompletableSchedulers()).subscribe({
                    setFavoriteState(true)
                }, { error ->
                    setFavoriteState(false)
                    Timber.e(error, "Failed save movie to database")
                })
            } else {
                db.delete(
                    FavoriteMovie(
                        movieId,
                        posterPath
                    )
                ).compose(addCompletableSchedulers())
                    .subscribe({
                        setFavoriteState(false)
                    }, { error ->
                        setFavoriteState(true)
                        Timber.e(error, "Failed delete movie from database") })
            }
        }

        details.credits.let { cast ->
            val artistList = cast.map {
                ArtistItem(it)
            }.toList()
            artists_recycler_view.adapter = adapter.apply { addAll(artistList) }
        }
    }

    private fun setFavoriteState(isChecked: Boolean) {
        favorite.setChecked(isChecked)
    }
}
