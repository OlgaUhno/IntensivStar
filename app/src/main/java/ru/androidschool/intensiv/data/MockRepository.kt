package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovieDetauls(title: String? = ""): MovieDetails {
        var actors = mutableListOf<Actor>()
        actors.add(Actor("Tobey Maguire", "https://www.themoviedb.org/t/p/w138_and_h175_face/kOJelnLSb89SeivbOCt1l94Hz2d.jpg"))

        actors.add(Actor("Willem Dafoe", "https://www.themoviedb.org/t/p/w138_and_h175_face/ui8e4sgZAwMPi3hzEO53jyBJF9B.jpg"))

        actors.add(Actor("Kirsten Dunst", "https://www.themoviedb.org/t/p/w138_and_h175_face/sFYHUU1gWd57pttD8732tkBsXV5.jpg"))

        actors.add(Actor("James Franco", "https://www.themoviedb.org/t/p/w138_and_h175_face/pgEe68922Ba20vAwPmAFQFm8U0c.jpg"))

        actors.add(Actor("Rosemary Harris", "https://www.themoviedb.org/t/p/w138_and_h175_face/rpSsXg0tB7wWQMffvvMqLzrvZAj.jpg"))

        actors.add(Actor("J.K. Simmons", "https://www.themoviedb.org/t/p/w138_and_h175_face/7kIiPojgSVNRXb5z0hiijcD5LJ6.jpg"))

        return MovieDetails(
            title = "Spider-Man",
            voteAverage = 9.0,
            posterPath = "https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg",
            overview = "After being bitten by a genetically altered spider at Oscorp, nerdy but endearing high school student Peter Parker is endowed with amazing powers to become the superhero known as Spider-Man.",
            productionCompanies = arrayOf("Warner Bros"),
            release = "03/05/2002",
            genres = arrayOf("Fantasy", "Action"),
            cast =  actors
        )
    }

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                posterPath = "https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg"
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
                posterPath = "https://upload.wikimedia.org/wikipedia/en/thumb/0/0b/HouseGregoryHouse.png/250px-HouseGregoryHouse.png"
            )
        )
        tvShowList.add(
            TvShow(
                title = "Breaking Bad",
                voteAverage = 9.5,
                posterPath = "https://upload.wikimedia.org/wikipedia/ru/thumb/4/4e/Breaking_Bad_%28season_4%29.jpg/274px-Breaking_Bad_%28season_4%29.jpg"
            )
        )
        tvShowList.add(
            TvShow(
                title = "Game of Thrones",
                voteAverage = 10.0,
                posterPath = "https://upload.wikimedia.org/wikipedia/ru/3/38/%D0%98%D0%B3%D1%80%D0%B0_%D0%9F%D1%80%D0%B5%D1%81%D1%82%D0%BE%D0%BB%D0%BE%D0%B2.jpg"
            )
        )
        return tvShowList
    }
}
