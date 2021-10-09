package ru.androidschool.intensiv.data

open class Movie(
    title: String? = "",
    voteAverage: Double = 0.0,
    posterPath: String? = ""
) : MovieBase (title,voteAverage, posterPath)
