package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.BuildConfig

fun getFullPosterPath(path: String?): String = (BuildConfig.BASE_POSTER_PATH + path)

fun getRating(voteAverage: Double?): Float = (voteAverage?.div(2)?.toFloat() ?: 0.0f)

fun toNonNullableString(value: String?): String = (value ?: "")
