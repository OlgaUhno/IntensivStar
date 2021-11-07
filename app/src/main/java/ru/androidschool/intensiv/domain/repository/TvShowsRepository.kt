package ru.androidschool.intensiv.domain.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.TvShowVo

interface TvShowsRepository {
    fun getShows(): Single<List<TvShowVo>>
}
