package ru.androidschool.intensiv.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.MoviesResponseDto
import ru.androidschool.intensiv.util.Constants

interface MovieApiInterface {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE
    ): Single<MoviesResponseDto>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE
    ): Single<MoviesResponseDto>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE
    ): Single<MoviesResponseDto>

    @GET("search/movie")
    fun searchByQuery(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("query") query: String
    ): Call<MoviesResponseDto>
}
