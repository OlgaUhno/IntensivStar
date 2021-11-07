package ru.androidschool.intensiv.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovies")
data class FavoriteMovie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?
)
