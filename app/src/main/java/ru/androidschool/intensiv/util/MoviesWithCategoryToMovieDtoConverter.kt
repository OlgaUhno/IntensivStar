package ru.androidschool.intensiv.util

import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.database.entities.Movie

object MoviesWithCategoryToMovieDtoConverter {
    fun toMovieDto(movies: List<Movie>): List<MovieDto> = movies.map {
        MovieDto(
            id = it.movieId,
            posterPath = it.posterPath,
            title = it.title,
            voteAverage = it.voteAverage
        )
    }.toList()
}
