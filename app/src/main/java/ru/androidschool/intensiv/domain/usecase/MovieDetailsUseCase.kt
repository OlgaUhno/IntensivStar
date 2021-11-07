package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.MovieDetailsVo
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository

class MovieDetailsUseCase(private val repository: MovieDetailsRepository) {
    fun getDetails(id: Int): Single<MovieDetailsVo> {
        return repository.getDetails(id).addSchedulers()
    }
}
