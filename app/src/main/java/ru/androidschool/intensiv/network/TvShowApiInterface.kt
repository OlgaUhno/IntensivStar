package ru.androidschool.intensiv.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.TvShowsResponseDto
import ru.androidschool.intensiv.util.Constants

interface TvShowApiInterface {
    @GET("tv/popular")
    fun getPopularShows(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE
    ): Single<TvShowsResponseDto>
}
