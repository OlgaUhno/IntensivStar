package ru.androidschool.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.WatchListMockRepository
import ru.androidschool.intensiv.domain.usecase.WatchListUseCase

class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        WatchListUseCase(WatchListMockRepository()).getMovies()
        val moviesList =
            WatchListUseCase(WatchListMockRepository()).getMovies().map {
                MoviePreviewItem(
                    it
                ) { movie -> }
            }.toList()

        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    companion object {

        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}