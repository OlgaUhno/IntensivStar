package ru.androidschool.intensiv.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.database.entities.Movie
import ru.androidschool.intensiv.data.database.entities.MovieCategory
import ru.androidschool.intensiv.data.database.relations.MoviesWithCategory

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: MovieCategory): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movies: List<Movie>): Completable

    @Transaction
    @Query("SELECT * FROM Movie WHERE category = :category")
    fun getCategoryWithMovies(category: Int): Single<MoviesWithCategory>

    @Transaction
    @Query("SELECT * FROM Movie")
    fun getCategoriesWithMovies(): Single<List<MoviesWithCategory>>

    @Query("DELETE FROM movie")
    fun deleteAll(): Completable
}
