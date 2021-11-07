package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.GenresDto
import ru.androidschool.intensiv.data.dto.MovieDetailsDto
import ru.androidschool.intensiv.data.dto.ProductionCompanyDto
import ru.androidschool.intensiv.data.vo.MovieDetailsVo

object MovieDetailsMapper {

    fun toViewObject(detailsDto: MovieDetailsDto?): MovieDetailsVo {
        return detailsDto?.let {
            MovieDetailsVo(
                id = detailsDto.id ?: -1,
                title = toNonNullableString(
                    detailsDto.title
                ),
                posterPath = getFullPosterPath(
                    detailsDto.posterPath
                ),
                rating = getRating(
                    detailsDto.voteAverage
                ),
                overview = toNonNullableString(
                    detailsDto.overview
                ),
                productionCompanies = detailsDto.productionCompanies.map(ProductionCompanyDto::name)
                    .joinToString(),
                release = detailsDto.release,
                genres = detailsDto.genres.map(GenresDto::name).joinToString(),
                cast = CastMapper.toViewObject(
                    detailsDto.credits.cast
                )
            )
        } ?: MovieDetailsVo()
    }
}
