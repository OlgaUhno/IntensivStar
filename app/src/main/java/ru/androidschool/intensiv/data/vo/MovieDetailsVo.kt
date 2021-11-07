package ru.androidschool.intensiv.data.vo

data class MovieDetailsVo(
    val id: Int = -1,
    val title: String = "",
    val posterPath: String = "",
    val rating: Float = 0.0f,
    val overview: String = "",
    val productionCompanies: String = "",
    val release: String = "",
    val genres: String = "",
    val cast: List<CastVo> = listOf()
)
