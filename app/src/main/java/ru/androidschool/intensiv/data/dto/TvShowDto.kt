package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class TvShowDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)
