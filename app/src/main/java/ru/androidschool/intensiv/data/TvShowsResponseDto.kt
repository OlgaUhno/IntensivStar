package ru.androidschool.intensiv.data

data class TvShowsResponseDto(
    var page: Int,
    var results: List<TvShowDto>
)
