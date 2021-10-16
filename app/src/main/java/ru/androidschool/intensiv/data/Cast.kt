package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class Cast(
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    override val posterPath: String?
) : PosterBase()
