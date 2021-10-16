package ru.androidschool.intensiv.data

object MockRepository {
    fun getMovies(): List<MovieDto> {

        val moviesList = mutableListOf<MovieDto>()
        for (x in 0..10) {
            val movie = MovieDto(
                id = 5,
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                posterPath = "https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg"
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShows(): List<TvShowDto> {

        val tvShowList = mutableListOf<TvShowDto>()
        tvShowList.add(
            TvShowDto(
                id = 1,
                name = "Doctor House",
                voteAverage = 8.0,
                posterPath = "https://upload.wikimedia.org/wikipedia/en/thumb/0/0b/HouseGregoryHouse.png/250px-HouseGregoryHouse.png"
            )
        )
        tvShowList.add(
            TvShowDto(
                id = 2,
                name = "Breaking Bad",
                voteAverage = 9.5,
                posterPath = "https://upload.wikimedia.org/wikipedia/ru/thumb/4/4e/Breaking_Bad_%28season_4%29.jpg/274px-Breaking_Bad_%28season_4%29.jpg"
            )
        )
        tvShowList.add(
            TvShowDto(
                id = 3,
                name = "Game of Thrones",
                voteAverage = 10.0,
                posterPath = "https://upload.wikimedia.org/wikipedia/ru/3/38/%D0%98%D0%B3%D1%80%D0%B0_%D0%9F%D1%80%D0%B5%D1%81%D1%82%D0%BE%D0%BB%D0%BE%D0%B2.jpg"
            )
        )
        return tvShowList
    }
}
