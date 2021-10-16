package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShowDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?
) : MovieBase()
