package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_detail_bottom_view.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDetailsDto
import ru.androidschool.intensiv.data.MovieDetailsVo
import ru.androidschool.intensiv.network.MovieDetailsApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.loadImage
import ru.androidschool.intensiv.util.MovieDetailsConvertor
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private lateinit var compositeDisposable: CompositeDisposable
    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(FeedFragment.KEY_ID)
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { dto: MovieDetailsDto? -> MovieDetailsConvertor.toViewObject(dto) }
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
        release.text = details.release
        description.text = details.overview
        movie_rating.rating = details.rating
        movie_name.text = details.title
        production.text = details.productionCompanies
        genres.text = details.genres

        // TODO: get state from provider or shared preferences
        favorite.setChecked(true)
        favorite.setOnCheckedChangeListener { buttonView, isChecked ->
            // TODO: save value
        }
        details.credits.let { cast ->
            val artistList = cast.map {
                ArtistItem(it)
            }.toList()
            artists_recycler_view.adapter = adapter.apply { addAll(artistList) }
        }
    }
}
