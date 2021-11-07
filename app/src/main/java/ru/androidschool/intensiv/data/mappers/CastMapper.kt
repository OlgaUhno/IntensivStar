package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.CastDto
import ru.androidschool.intensiv.data.vo.CastVo

object CastMapper {
    fun toViewObject(castDto: List<CastDto>): List<CastVo> {
        return castDto.map {
            CastVo(
                name = toNonNullableString(
                    it.name
                ),
                posterPath = getFullPosterPath(
                    it.posterPath
                )
            )
        }.toList()
    }
}
