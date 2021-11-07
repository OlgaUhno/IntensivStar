package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class TopRatedMoviesRemoteRepository : MoviesRepository {

    override fun getMovies(): Single<List<MovieVo>> {
        return MovieApiClient.apiClient.getTopRatedMovies()
            .map { MovieMapper.toViewObject(it) }
    }
}
