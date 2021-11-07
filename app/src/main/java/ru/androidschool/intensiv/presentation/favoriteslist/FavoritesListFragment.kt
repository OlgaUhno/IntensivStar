package ru.androidschool.intensiv.presentation.favoriteslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favoritelist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.FavoritesMoviesRepository
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie
import ru.androidschool.intensiv.domain.usecase.FavoritesMoviesUseCase
import timber.log.Timber

class FavoritesListFragment : Fragment(R.layout.fragment_favoritelist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()
        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)

        FavoritesMoviesUseCase(FavoritesMoviesRepository()).getMovies(requireContext())
            .subscribe({ result ->
                updateMoviesList(result)
            }, { error -> Timber.e(error, "Failed get movies") })
    }

    private fun updateMoviesList(movies: List<FavoriteMovie>) {
        val moviesList = movies.map {
            FavoritesItem(it)
        }.toList()
        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesListFragment()
    }
}
