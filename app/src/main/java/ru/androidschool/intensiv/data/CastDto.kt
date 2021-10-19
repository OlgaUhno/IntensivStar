package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class CastDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val posterPath: String?
)
