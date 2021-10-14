package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.TvShowsResponse

interface TvShowApiInterface {
    @GET("tv/popular")
    fun getPopularShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvShowsResponse>
}
