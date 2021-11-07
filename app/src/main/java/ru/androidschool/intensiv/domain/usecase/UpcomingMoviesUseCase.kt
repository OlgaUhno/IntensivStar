package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class UpcomingMoviesUseCase(private val repository: MoviesRepository) {
    fun getMovies(): Single<List<MovieVo>> {
        return repository.getMovies().compose(addSingleSchedulers())
    }
}
