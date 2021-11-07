package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_detail_bottom_view.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.vo.MovieDetailsVo
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie
import ru.androidschool.intensiv.data.repository.FavoritesMoviesRepository
import ru.androidschool.intensiv.presentation.loadImage
import ru.androidschool.intensiv.data.repository.MovieDetailsRemoteRepository
import ru.androidschool.intensiv.domain.usecase.FavoritesMoviesUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailsUseCase
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
            MovieDetailsUseCase(MovieDetailsRemoteRepository()).getDetails(movieId)
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

        val useCase = FavoritesMoviesUseCase(FavoritesMoviesRepository())
        useCase.isMovieExist(requireContext(), movieId)
            .subscribe({
                setFavoriteState(it)
            }, { error -> Timber.e(error, "Failed get movie from database") })

        favorite.setOnCheckedChangeListener { buttonView, isChecked ->
            val movie = FavoriteMovie(
                movieId,
                posterPath
            )

            if (isChecked) {
                useCase.add(requireContext(), movie)
                    .subscribe({
                        setFavoriteState(true)
                    }, { error ->
                        setFavoriteState(false)
                        Timber.e(error, "Failed save movie")
                    })
            } else {
                useCase.delete(requireContext(), movie)
                    .subscribe({
                        setFavoriteState(false)
                    }, { error ->
                        setFavoriteState(true)
                        Timber.e(error, "Failed delete movie")
                    })
            }
        }

        details.cast.let { cast ->
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
