package ru.androidschool.intensiv.presentation.favoriteslist

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie
import ru.androidschool.intensiv.presentation.loadImage

class FavoritesItem(
    private val content: FavoriteMovie
) : Item() {

    override fun getLayout() = R.layout.item_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_preview.loadImage(content.posterPath)
    }
}
