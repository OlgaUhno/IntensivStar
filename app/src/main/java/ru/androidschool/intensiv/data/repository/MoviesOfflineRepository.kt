package ru.androidschool.intensiv.data.repository

import android.content.Context
import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieCategories
import ru.androidschool.intensiv.data.database.MovieDao
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.database.entities.Movie
import ru.androidschool.intensiv.data.database.relations.MoviesWithCategory
import ru.androidschool.intensiv.data.mappers.MoviesWithCategoryMapper
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesResultRepository
import ru.androidschool.intensiv.domain.usecase.addCompletableSchedulers
import timber.log.Timber

class MoviesOfflineRepository : MoviesResultRepository {

    override fun getMovies(context: Context): Single<HashMap<MovieCategories, List<MovieVo>>> {
        val db: MovieDao = MovieDatabase.get(context).movieCategoryDao()

        return db.getCategoriesWithMovies()
            .map { result: List<MoviesWithCategory> ->
                val hashMap = HashMap<MovieCategories, List<MovieVo>>()
                result.map {
                    val moviesList: List<MovieVo> =
                        MoviesWithCategoryMapper.toMovieVo(it.movies)
                    val category: MovieCategories? =
                        MovieCategories.fromInt(it.movieCategory.category)
                    hashMap.put(category ?: MovieCategories.TOP, moviesList)
                }
                hashMap
            }
    }

    override fun saveMovies(context: Context, result: HashMap<MovieCategories, List<MovieVo>>) {
        val cashedList: List<Movie> = result.entries.flatMap { MoviesWithCategoryMapper.toMovie(it.value, it.key.type) }

        val db: MovieDao = MovieDatabase.get(context).movieCategoryDao()
        db.deleteAll()
            .concatWith(db.save(cashedList))
            .compose(addCompletableSchedulers()).subscribe({
                Timber.i("Movies have been saved to database")
            }, { error ->
                Timber.e(error, "Failed save movies to database")
            })
    }
}
