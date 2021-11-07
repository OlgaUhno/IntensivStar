package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.database.entities.Movie
import ru.androidschool.intensiv.data.vo.MovieVo

object MoviesWithCategoryMapper {
    fun toMovieVo(movies: List<Movie>): List<MovieVo> = movies.map {
        MovieVo(
            id = it.movieId,
            posterPath = it.posterPath,
            title = it.title,
            rating = it.rating
        )
    }.toList()

    fun toMovie(movies: List<MovieVo>, category: Int): List<Movie> = movies.map {
        Movie(
            movieId = it.id,
            posterPath = it.posterPath,
            title = it.title,
            rating = it.rating,
            category = category
        )
    }.toList()
}
