package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.mappers.TvShowMapper
import ru.androidschool.intensiv.data.network.TvShowApiClient
import ru.androidschool.intensiv.data.vo.TvShowVo
import ru.androidschool.intensiv.domain.repository.TvShowsRepository

class TvShowsRemoteRepository : TvShowsRepository {

    override fun getShows(): Single<List<TvShowVo>> {
        return TvShowApiClient.apiClient.getPopularShows()
            .map { TvShowMapper.toViewObject(it) }
    }
}
