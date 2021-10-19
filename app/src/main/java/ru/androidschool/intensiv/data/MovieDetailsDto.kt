package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("production_companies")
    var productionCompanies: Array<ProductionCompanyDto>,
    @SerializedName("release_date")
    var release: String,
    @SerializedName("genres")
    var genres: Array<GenresDto>,
    @SerializedName("credits")
    var credits: CreditsResponseDto
)
