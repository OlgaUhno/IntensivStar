package ru.androidschool.intensiv.domain.usecase

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function3
import ru.androidschool.intensiv.data.MovieCategories
import ru.androidschool.intensiv.data.repository.MoviesOfflineRepository
import ru.androidschool.intensiv.data.repository.NowPlayingMoviesRemoteRepository
import ru.androidschool.intensiv.data.repository.TopRatedMoviesRemoteRepository
import ru.androidschool.intensiv.data.repository.UpcomingMoviesRemoteRepository
import ru.androidschool.intensiv.data.vo.MovieVo
import ru.androidschool.intensiv.domain.repository.MoviesResultRepository

class FeedMoviesUseCase(private val firstRepository: MoviesResultRepository) {
    fun getMovies(context: Context): Flowable<HashMap<MovieCategories, List<MovieVo>>> {
        val remoteObservable = createRemoteSingleObservable(context)

        return firstRepository.getMovies(context)
            .onErrorResumeNext(remoteObservable)
            .concatWith(remoteObservable)
            .compose(addFlowableSchedulers())
    }

    private fun createRemoteSingleObservable(context: Context): Single<HashMap<MovieCategories, List<MovieVo>>> {
        val playNow = NowPlayingMoviesUseCase(NowPlayingMoviesRemoteRepository()).getMovies()
        val topRated = TopRatedMoviesUseCase(TopRatedMoviesRemoteRepository()).getMovies()
        val upcoming = UpcomingMoviesUseCase(UpcomingMoviesRemoteRepository()).getMovies()

        return Single.zip(
            playNow, topRated, upcoming,
            Function3<List<MovieVo>, List<MovieVo>, List<MovieVo>, HashMap<MovieCategories, List<MovieVo>>> { nowResult: List<MovieVo>, topResult: List<MovieVo>, upcomingResult: List<MovieVo> ->
                hashMapOf(
                    MovieCategories.TOP to topResult,
                    MovieCategories.NOW to nowResult,
                    MovieCategories.UPCOMING to upcomingResult
                )
            }
        )
            .doOnSuccess { results: HashMap<MovieCategories, List<MovieVo>> -> saveMovies(context, results) }
    }

    fun saveMovies(context: Context, result: HashMap<MovieCategories, List<MovieVo>>) {
        MoviesOfflineRepository().saveMovies(context, result)
    }
}
