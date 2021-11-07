package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class GenresDto(
    @SerializedName("name")
    val name: String?
)
