package ru.androidschool.intensiv.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movie: FavoriteMovie): Completable

    @Delete
    fun delete(movie: FavoriteMovie): Completable

    @Query("SELECT EXISTS(SELECT * FROM FavoriteMovies WHERE movieId = :movieId)")
    fun isMovieExist(movieId: Int): Single<Boolean>

    @Query("SELECT * FROM FavoriteMovies")
    fun getMovies(): Single<List<FavoriteMovie>>
}
