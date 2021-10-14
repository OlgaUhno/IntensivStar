package ru.androidschool.intensiv.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.androidschool.intensiv.util.HttpLogging

fun getOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor(HttpLogging()).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    })
    .build()