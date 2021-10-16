package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?
) : MovieBase()
