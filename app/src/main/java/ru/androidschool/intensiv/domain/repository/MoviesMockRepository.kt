package ru.androidschool.intensiv.domain.repository

import ru.androidschool.intensiv.data.vo.MovieVo

interface MoviesMockRepository {
    fun getMovies(): List<MovieVo>
}
