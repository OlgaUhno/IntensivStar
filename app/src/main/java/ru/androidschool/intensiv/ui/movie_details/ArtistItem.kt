package ru.androidschool.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_artist.*
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.data.Movie

class ArtistItem(
    private val content: Actor
) : Item() {

    override fun getLayout() = R.layout.item_artist

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.artist_name.text = content.name

        // TODO Получать из модели
        Picasso.get()
            .load(content.profile)
            .into(viewHolder.artist_profile)
    }
}
