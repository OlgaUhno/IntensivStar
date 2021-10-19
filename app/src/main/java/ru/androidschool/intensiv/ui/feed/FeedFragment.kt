package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.util.addSchedulers
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
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

        compositeDisposable.add(
            MovieApiClient.apiClient.getNowPlayingMovies()
                .compose(addSchedulers())
                .subscribe({ response ->
                    addResults(
                        response.results,
                        R.string.now_playing
                    )
                }, { error -> onFailure(error, "Failed get top now playing movies") })
        )

        compositeDisposable.add(
            MovieApiClient.apiClient.getTopRatedMovies()
                .compose(addSchedulers())
                .subscribe({ response ->
                    addResults(
                        response.results,
                        R.string.recommended
                    )
                }, { error -> onFailure(error, "Failed get top rated movies") })
        )

        compositeDisposable.add(
            MovieApiClient.apiClient.getTopRatedMovies()
                .compose(addSchedulers())
                .subscribe({ response ->
                    addResults(
                        response.results,
                        R.string.upcoming
                    )
                }, { error -> onFailure(error, "Failed get upcoming movies") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun onFailure(e: Throwable, message: String) {
        Timber.e(e, message)
    }

    private fun addResults(result: List<MovieDto>?, @StringRes title: Int) {
        result?.let {
            val resultList = listOf(
                MainCardContainer(
                    title,
                    result.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(movie)
                        }
                    }.toList()
                )
            )

            movies_recycler_view.adapter = adapter.apply { addAll(resultList) }
        }
    }

    private fun openMovieDetails(movie: MovieDto) {
        val bundle = Bundle()
        // TODO: change to use safe arguments
        bundle.putInt(KEY_ID, movie.id ?: -1)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
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
        const val KEY_ID = "id"
    }
}
