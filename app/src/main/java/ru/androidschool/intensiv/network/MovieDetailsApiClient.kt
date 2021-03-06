package ru.androidschool.intensiv.network

object MovieDetailsApiClient {
    val apiClient: MovieDetailsApiInterface by lazy {
        val retrofit = getRetrofitInstance()

        return@lazy retrofit.create(MovieDetailsApiInterface::class.java)
    }
}
