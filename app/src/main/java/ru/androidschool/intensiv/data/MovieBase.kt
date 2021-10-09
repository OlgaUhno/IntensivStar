package ru.androidschool.intensiv.data

open class MovieBase(
    var title: String? = "",
    var voteAverage: Double = 0.0,
    var posterPath: String? = ""
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}