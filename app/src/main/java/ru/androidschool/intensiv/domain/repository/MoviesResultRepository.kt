package ru.androidschool.intensiv.domain.repository

import android.content.Context
import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieCategories
import ru.androidschool.intensiv.data.vo.MovieVo

interface MoviesResultRepository {
    fun getMovies(context: Context): Single<HashMap<MovieCategories, List<MovieVo>>>

    fun saveMovies(context: Context, result: HashMap<MovieCategories, List<MovieVo>>)
}
