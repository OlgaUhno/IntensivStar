package ru.androidschool.intensiv.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.androidschool.intensiv.database.entities.Movie
import ru.androidschool.intensiv.database.entities.MovieCategory

data class MoviesWithCategory(
    @Embedded val movieCategory: MovieCategory,
    @Relation(
        parentColumn = "category",
        entityColumn = "category"
    )
    val movies: List<Movie>
)
