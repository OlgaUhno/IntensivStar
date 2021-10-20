package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.data.MoviesResponseDto
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.rx.addSchedulers
import ru.androidschool.intensiv.ui.navigationOptions
import ru.androidschool.intensiv.util.Constants
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FeedFragment created")
        adapter.clear()

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        compositeDisposable = CompositeDisposable()

        val playNowSource = prepareSource(MovieApiClient.apiClient.getNowPlayingMovies())
        val topRatedSource = prepareSource(MovieApiClient.apiClient.getTopRatedMovies())
        val upcomingSource = prepareSource(MovieApiClient.apiClient.getUpcomingMovies())

        compositeDisposable.add(
            Single.zip(
                playNowSource, topRatedSource, upcomingSource,
                Function3<MoviesResponseDto, MoviesResponseDto, MoviesResponseDto, List<MainCardContainer>> { now, top, upcoming ->
                    listOf(
                        toCardContainer(now.results, R.string.now_playing),
                        toCardContainer(top.results, R.string.recommended),
                        toCardContainer(upcoming.results, R.string.upcoming)
                    )
                }
            ).compose(addSchedulers(movies_progress_bar))
                .subscribe({ result ->
                    movies_recycler_view.adapter = adapter.apply { addAll(result) }
                }, { error -> Timber.e(error, "Failed get movies") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun prepareSource(source: Single<MoviesResponseDto>) = source.compose(addSchedulers())

    private fun toCardContainer(
        result: List<MovieDto>,
        @StringRes title: Int
    ): MainCardContainer {
        return MainCardContainer(
            title,
            result.map {
                MovieItem(it) { movie ->
                    openMovieDetails(movie)
                }
            }.toList()
        )
    }

    private fun openMovieDetails(movie: MovieDto) {
        val bundle = Bundle()
        // TODO: change to use safe arguments
        bundle.putInt(Constants.KEY_ID, movie.id ?: -1)
        findNavController().navigate(R.id.movie_details_fragment, bundle, navigationOptions)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, navigationOptions)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
    }
}
