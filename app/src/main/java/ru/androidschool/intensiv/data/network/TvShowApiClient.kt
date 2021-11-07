package ru.androidschool.intensiv.data.network

object TvShowApiClient {
    val apiClient: TvShowApiInterface by lazy {
        val retrofit = getRetrofitInstance()

        return@lazy retrofit.create(TvShowApiInterface::class.java)
    }
}
