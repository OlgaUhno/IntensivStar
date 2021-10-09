package ru.androidschool.intensiv.data

open class MovieDetails(
    title: String? = "",
    voteAverage: Double = 0.0,
    posterPath: String? = "",
    var overview: String? = "",
    var productionCompanies: Array<String>,
    var release: String,
    var genres: Array<String>,
    var cast: List<Actor>
) : MovieBase (title, voteAverage, posterPath)
