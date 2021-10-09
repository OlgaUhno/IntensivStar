package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShows(): List<TvShow> {

        val tvShowList = mutableListOf<TvShow>()
        tvShowList.add(
            TvShow(
            title = "Doctor House",
            voteAverage = 8.0,
            cover = "https://upload.wikimedia.org/wikipedia/en/thumb/0/0b/HouseGregoryHouse.png/250px-HouseGregoryHouse.png"
        ))
        tvShowList.add(
            TvShow(
                title = "Breaking Bad",
                voteAverage = 9.5,
                cover = "https://upload.wikimedia.org/wikipedia/ru/thumb/4/4e/Breaking_Bad_%28season_4%29.jpg/274px-Breaking_Bad_%28season_4%29.jpg"
            ))
        tvShowList.add(
            TvShow(
                title = "Game of Thrones",
                voteAverage = 10.0,
                cover = "https://upload.wikimedia.org/wikipedia/ru/3/38/%D0%98%D0%B3%D1%80%D0%B0_%D0%9F%D1%80%D0%B5%D1%81%D1%82%D0%BE%D0%BB%D0%BE%D0%B2.jpg"
            ))
        return tvShowList
    }
}
