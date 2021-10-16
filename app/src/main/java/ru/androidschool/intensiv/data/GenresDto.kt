package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class GenresDto(
    @SerializedName("name")
    val name: String?
)
