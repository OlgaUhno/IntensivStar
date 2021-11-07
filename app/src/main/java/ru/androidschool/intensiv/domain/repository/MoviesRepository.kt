package ru.androidschool.intensiv.domain.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.MovieVo

interface MoviesRepository {
    fun getMovies(): Single<List<MovieVo>>
}
