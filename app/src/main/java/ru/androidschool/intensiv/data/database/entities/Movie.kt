package ru.androidschool.intensiv.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "posterPath")
    val posterPath: String,
    @ColumnInfo(name = "voteAverage")
    val rating: Float,
    @ColumnInfo(name = "title")
    val title: String,
    val category: Int
)
