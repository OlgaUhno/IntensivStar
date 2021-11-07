package ru.androidschool.intensiv.data.dto

data class MoviesResponseDto(
    var page: Int,
    var results: List<MovieDto>
)
