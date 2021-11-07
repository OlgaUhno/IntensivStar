package ru.androidschool.intensiv.domain.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.MovieDetailsVo

interface MovieDetailsRepository {
    fun getDetails(id: Int): Single<MovieDetailsVo>
}
