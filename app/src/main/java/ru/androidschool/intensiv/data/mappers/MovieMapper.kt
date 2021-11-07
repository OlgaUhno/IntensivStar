package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.MovieDto
import ru.androidschool.intensiv.data.dto.MoviesResponseDto
import ru.androidschool.intensiv.data.vo.MovieVo

object MovieMapper {

    fun toViewObject(dto: MoviesResponseDto): List<MovieVo> {
        return dto.results.map { toViewObject(it) }
    }

    fun toViewObject(movieDto: MovieDto): MovieVo {
        return MovieVo(
            id = movieDto.id ?: -1,
            posterPath = getFullPosterPath(
                movieDto.posterPath
            ),
            rating = getRating(
                movieDto.voteAverage
            ),
            title = toNonNullableString(
                movieDto.title
            )
        )
    }
}
