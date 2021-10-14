package ru.androidschool.intensiv.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.util.Constants
import ru.androidschool.intensiv.util.HttpLogging

object TvShowApiClient {
    private var client: OkHttpClient = getOkHttpClient()

    val apiClient: TvShowApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(TvShowApiInterface::class.java)
    }
}
