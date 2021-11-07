package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesMockRepository

class WatchListMockRepository : MoviesMockRepository {
    override fun getMovies(): List<MovieVo> {
        return MockRepository.getMovies()
            .map { MovieMapper.toViewObject(it) }
    }
}
