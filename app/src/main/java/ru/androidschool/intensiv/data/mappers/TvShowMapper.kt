package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.TvShowDto
import ru.androidschool.intensiv.data.dto.TvShowsResponseDto
import ru.androidschool.intensiv.data.vo.TvShowVo

object TvShowMapper {

    fun toViewObject(dto: TvShowsResponseDto): List<TvShowVo> {
        return dto.results.map { toViewObject(it) }
    }

    fun toViewObject(tvShowDto: TvShowDto): TvShowVo {
        return TvShowVo(
            id = tvShowDto.id ?: -1,
            posterPath = getFullPosterPath(
                tvShowDto.posterPath
            ),
            rating = getRating(
                tvShowDto.voteAverage
            ),
            name = toNonNullableString(
                tvShowDto.name
            )
        )
    }
}
