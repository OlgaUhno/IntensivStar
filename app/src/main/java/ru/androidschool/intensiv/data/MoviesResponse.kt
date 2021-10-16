package ru.androidschool.intensiv.data

data class MoviesResponse(
    var page: Int,
    var results: List<Movie>
)
