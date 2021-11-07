package ru.androidschool.intensiv.domain.repository

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie

interface FavoritesRepository {
    fun getMovies(context: Context): Single<List<FavoriteMovie>>

    fun isMovieExist(context: Context, id: Int): Single<Boolean>

    fun add(context: Context, movie: FavoriteMovie): Completable

    fun delete(context: Context, movie: FavoriteMovie): Completable
}
