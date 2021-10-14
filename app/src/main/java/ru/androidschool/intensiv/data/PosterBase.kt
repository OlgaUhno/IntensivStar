package ru.androidschool.intensiv.data

private const val BASE_PATH = "https://image.tmdb.org/t/p/w500"

abstract class PosterBase {
    open val posterPath: String? = null

    val poster: String?
        get() = (BASE_PATH + posterPath)
}
