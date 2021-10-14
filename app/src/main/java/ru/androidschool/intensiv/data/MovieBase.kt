package ru.androidschool.intensiv.data

abstract class MovieBase : PosterBase() {
    open val voteAverage: Double? = 0.0

    val rating: Float
        get() = voteAverage?.div(2)?.toFloat() ?: 0.0f
}
