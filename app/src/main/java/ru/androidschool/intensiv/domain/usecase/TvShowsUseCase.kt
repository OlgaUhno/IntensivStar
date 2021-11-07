package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.TvShowVo
import ru.androidschool.intensiv.domain.repository.TvShowsRepository

class TvShowsUseCase(private val repository: TvShowsRepository) {
    fun getShows(): Single<List<TvShowVo>> {
        return repository.getShows().compose(addSingleSchedulers())
    }
}
