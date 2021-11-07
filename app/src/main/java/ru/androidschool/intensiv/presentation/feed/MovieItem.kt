package ru.androidschool.intensiv.presentation.feed

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.loadImage
import ru.androidschool.intensiv.data.vo.MovieVo

class MovieItem(
    private val content: MovieVo,
    private val onClick: (movie: MovieVo) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.image_preview.loadImage(content.posterPath)
    }
}
