package ru.androidschool.intensiv.data.repository

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.database.FavoriteMovieDao
import ru.androidschool.intensiv.data.database.FavoriteMoviesDatabase
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie
import ru.androidschool.intensiv.domain.repository.FavoritesRepository

class FavoritesMoviesRepository : FavoritesRepository {

    override fun getMovies(context: Context): Single<List<FavoriteMovie>> {

        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(context).movieDao()

        return db.getMovies()
    }

    override fun isMovieExist(context: Context, id: Int): Single<Boolean> {
        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(context).movieDao()
        return db.isMovieExist(id)
    }

    override fun add(context: Context, movie: FavoriteMovie): Completable {
        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(context).movieDao()
        return db.save(movie)
    }

    override fun delete(context: Context, movie: FavoriteMovie): Completable {
        val db: FavoriteMovieDao = FavoriteMoviesDatabase.get(context).movieDao()
        return db.delete(movie)
    }
}
