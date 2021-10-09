package ru.androidschool.intensiv.data

class TvShow(
    title: String?,
    voteAverage: Double,
    var cover: String
) : Movie(title, voteAverage)
