package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.mappers.MovieDetailsMapper
import ru.androidschool.intensiv.data.network.MovieDetailsApiClient
import ru.androidschool.intensiv.data.vo.MovieDetailsVo
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository

class MovieDetailsRemoteRepository : MovieDetailsRepository {
    override fun getDetails(id: Int): Single<MovieDetailsVo> {
        return MovieDetailsApiClient.apiClient.getMovieDetails(id)
            .map { MovieDetailsMapper.toViewObject(it) }
    }
}
