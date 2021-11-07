package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.afterTextChanged
import ru.androidschool.intensiv.presentation.navigationOptions
import ru.androidschool.intensiv.util.Constants
import ru.androidschool.intensiv.data.MovieCategories
import ru.androidschool.intensiv.data.repository.MoviesOfflineRepository
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.usecase.*
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

        compositeDisposable.add(
            FeedMoviesUseCase(MoviesOfflineRepository()).getMovies(requireContext())
                .subscribe({ result ->
                    setResults(result)
                }, { error -> Timber.e(error, "Failed get movies") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun setResults(result: HashMap<MovieCategories, List<MovieVo>>) {
        val resultsList: List<MainCardContainer> =
            result.entries.map { toCardContainer(it.value, getCardTitleResId(it.key)) }
        movies_recycler_view.adapter =
            adapter.apply { addAll(resultsList) }
    }

    private fun getCardTitleResId(category: MovieCategories): Int {
        return when (category) {
            MovieCategories.TOP -> R.string.recommended
            MovieCategories.NOW -> R.string.now_playing
            MovieCategories.UPCOMING -> R.string.upcoming
        }
    }

    private fun toCardContainer(
        result: List<MovieVo>,
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

    private fun openMovieDetails(movie: MovieVo) {
        val bundle = Bundle()
        // TODO: change to use safe arguments
        bundle.putInt(Constants.KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle,
            navigationOptions
        )
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle,
            navigationOptions
        )
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
