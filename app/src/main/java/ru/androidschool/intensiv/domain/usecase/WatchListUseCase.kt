package ru.androidschool.intensiv.domain.usecase

import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesMockRepository

class WatchListUseCase(private val repository: MoviesMockRepository) {
    fun getMovies(): List<MovieVo> {
        return repository.getMovies()
    }
}
