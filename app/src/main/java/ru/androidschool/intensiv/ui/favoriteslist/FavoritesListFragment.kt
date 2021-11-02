package ru.androidschool.intensiv.ui.favoriteslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favoritelist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.database.FavoriteMoviesDatabase
import ru.androidschool.intensiv.database.entities.FavoriteMovie
import ru.androidschool.intensiv.database.FavoriteMovieDao
import ru.androidschool.intensiv.rx.addSingleSchedulers
import timber.log.Timber

class FavoritesListFragment : Fragment(R.layout.fragment_favoritelist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()
        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)

        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(requireContext()).movieDao()

        db.getMovies()
            .compose(addSingleSchedulers())
            .subscribe({ result ->
                updateMoviesList(result)
            }, { error -> Timber.e(error, "Failed get movies from database") })
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
