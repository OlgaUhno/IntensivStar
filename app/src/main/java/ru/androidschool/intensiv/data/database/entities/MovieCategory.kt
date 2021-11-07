package ru.androidschool.intensiv.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCategory(
    @PrimaryKey(autoGenerate = false)
    val category: Int
)
