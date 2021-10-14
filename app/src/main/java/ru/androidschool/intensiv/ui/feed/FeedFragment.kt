package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.data.MoviesResponseDto
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
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

        val getNowPlayingMovies =
            MovieApiClient.apiClient.getNowPlayingMovies()

        getNowPlayingMovies.enqueue(object : Callback<MoviesResponseDto> {
            override fun onFailure(call: Call<MoviesResponseDto>, e: Throwable) {
                Timber.e(e, "Failed get top now playing movies")
            }

            override fun onResponse(
                call: Call<MoviesResponseDto>,
                response: Response<MoviesResponseDto>
            ) {
                addResults(response.body()?.results, R.string.now_playing)
            }
        })

        val getTopRatedMovies =
            MovieApiClient.apiClient.getTopRatedMovies()

        getTopRatedMovies.enqueue(object : Callback<MoviesResponseDto> {
            override fun onFailure(call: Call<MoviesResponseDto>, e: Throwable) {
                Timber.e(e, "Failed get top rated movies")
            }

            override fun onResponse(
                call: Call<MoviesResponseDto>,
                response: Response<MoviesResponseDto>
            ) {
                addResults(response.body()?.results, R.string.recommended)
            }
        })

        val getUpcomingMovies =
            MovieApiClient.apiClient.getUpcomingMovies()

        getUpcomingMovies.enqueue(object : Callback<MoviesResponseDto> {
            override fun onFailure(call: Call<MoviesResponseDto>, e: Throwable) {
                Timber.e(e, "Failed get upcoming movies")
            }

            override fun onResponse(
                call: Call<MoviesResponseDto>,
                response: Response<MoviesResponseDto>
            ) {
                addResults(response.body()?.results, R.string.upcoming)
            }
        })
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
