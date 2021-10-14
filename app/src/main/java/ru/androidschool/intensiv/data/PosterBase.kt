package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.BuildConfig

abstract class PosterBase {
    open val posterPath: String? = null

    val poster: String?
        get() = (BuildConfig.BASE_POSTER_PATH + posterPath)
}
