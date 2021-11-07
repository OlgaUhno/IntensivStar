package ru.androidschool.intensiv.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.progress_indicator.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.usecase.addSingleProgress
import ru.androidschool.intensiv.domain.usecase.addSingleSchedulers
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.presentation.feed.MovieItem
import ru.androidschool.intensiv.presentation.navigationOptions
import ru.androidschool.intensiv.util.Constants
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString(KEY_SEARCH)
        search_toolbar.setText(searchTerm)

        adapter.clear()

        compositeDisposable = CompositeDisposable()

        searchTerm?.let {
            compositeDisposable.add(
                MovieApiClient.apiClient.searchByQuery(query = searchTerm)
                    .compose(addSingleSchedulers())
                    .compose(
                        addSingleProgress(
                            progress_bar
                        )
                    )
                    .subscribe({ response ->
                        response.results.let { results ->
                            val movieList = results.map {
                                MovieItem(
                                    MovieMapper.toViewObject(it)
                                ) { movie ->
                                    openMovieDetails(movie)
                                }
                            }.toList()

                            movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
                        }
                    }, { error -> Timber.e(error, "Failed get search result") })
            )
        }
    }

    private fun openMovieDetails(movie: MovieVo) {
        val bundle = Bundle()
        // TODO: change to use safe arguments
        bundle.putInt(Constants.KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle,
            navigationOptions
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
