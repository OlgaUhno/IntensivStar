package ru.androidschool.intensiv.network

object MovieApiClient {
    val apiClient: MovieApiInterface by lazy {
        val retrofit = getRetrofitInstance()

        return@lazy retrofit.create(MovieApiInterface::class.java)
    }
}
