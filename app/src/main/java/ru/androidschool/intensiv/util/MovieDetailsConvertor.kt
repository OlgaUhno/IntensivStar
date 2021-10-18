package ru.androidschool.intensiv.util

import ru.androidschool.intensiv.data.*

object MovieDetailsConvertor {

    fun toViewObject(detailsDto: MovieDetailsDto?): MovieDetailsVo {
        return detailsDto?.let {
            MovieDetailsVo(
                id = detailsDto.id ?: -1,
                title = toNonNullableString(detailsDto.title),
                posterPath = getFullPosterPath(detailsDto.posterPath),
                rating = getRating(detailsDto.voteAverage),
                overview = toNonNullableString(detailsDto.overview),
                productionCompanies = detailsDto.productionCompanies.map(ProductionCompanyDto::name)
                    .joinToString(),
                release = detailsDto.release,
                genres = detailsDto.genres.map(GenresDto::name).joinToString(),
                credits = toViewObject(detailsDto.credits.cast)
            )
        } ?: MovieDetailsVo()
    }

    fun toViewObject(castDto: List<CastDto>): List<CastVo> {
        return castDto.map {
            CastVo(
                name = toNonNullableString(it.name),
                posterPath = getFullPosterPath(it.posterPath)
            )
        }.toList()
    }
}
