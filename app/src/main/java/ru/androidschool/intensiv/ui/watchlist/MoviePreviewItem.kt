package ru.androidschool.intensiv.ui.watchlist

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.ui.loadImage

class MoviePreviewItem(
    private val content: MovieDto,
    private val onClick: (movie: MovieDto) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.image_preview.loadImage("https://www.kinopoisk.ru/images/film_big/1143242.jpg")
    }
}
