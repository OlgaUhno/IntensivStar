package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("name")
    val name: String?
)
