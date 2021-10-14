package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class MovieDetails(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?,
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("production_companies")
    var productionCompanies: Array<ProductionCompany>,
    @SerializedName("release_date")
    var release: String,
    @SerializedName("genres")
    var genres: Array<Genres>,
    @SerializedName("credits")
    var credits: CreditsResponse
) : MovieBase()
