package ru.androidschool.intensiv.data

object MockRepository {
    fun getMovies(): List<MovieDto> {

        val moviesList = mutableListOf<MovieDto>()
        for (x in 0..10) {
            val movie = MovieDto(
                id = 5,
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                posterPath = "https://www.kinopoisk.ru/images/film_big/1143242.jpg"
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
