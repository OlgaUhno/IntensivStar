package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.MovieDetailsDto
import ru.androidschool.intensiv.util.Constants

interface MovieDetailsApiInterface {
    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("append_to_response") appendToResponse: String = "credits"
    ): Call<MovieDetailsDto>
}
