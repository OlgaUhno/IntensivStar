package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.data.dto.MovieDto

object MockRepository {
    fun getMovies(): List<MovieDto> {

        val moviesList = mutableListOf<MovieDto>()
        for (x in 0..10) {
            val movie = MovieDto(
                id = 5,
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                posterPath = "/pUc51UUQb1lMLVVkDCaZVsCo37U.jpg"
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
