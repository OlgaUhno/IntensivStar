package ru.androidschool.intensiv.ui.feed

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.ui.loadImage
import ru.androidschool.intensiv.util.getFullPosterPath
import ru.androidschool.intensiv.util.getRating

class MovieItem(
    private val content: MovieDto,
    private val onClick: (movie: MovieDto) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = getRating(content.voteAverage)
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.image_preview.loadImage(getFullPosterPath(content.posterPath))
    }
}
