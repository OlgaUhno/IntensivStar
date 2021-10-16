package ru.androidschool.intensiv.data

data class MoviesResponseDto(
    var page: Int,
    var results: List<MovieDto>
)
