package ru.androidschool.intensiv.domain.usecase

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie
import ru.androidschool.intensiv.domain.repository.FavoritesRepository

class FavoritesMoviesUseCase(private val repository: FavoritesRepository) {
    fun getMovies(context: Context): Single<List<FavoriteMovie>> {
        return repository.getMovies(context).compose(addSingleSchedulers())
    }

    fun isMovieExist(context: Context, id: Int): Single<Boolean> {
        return repository.isMovieExist(context, id).compose(addSingleSchedulers())
    }

    fun add(context: Context, movie: FavoriteMovie): Completable {
        return repository.add(context, movie).compose(addCompletableSchedulers())
    }

    fun delete(context: Context, movie: FavoriteMovie): Completable {
        return repository.delete(context, movie).compose(addCompletableSchedulers())
    }
}
