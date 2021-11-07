package ru.androidschool.intensiv.presentation.movie_details

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_artist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.vo.CastVo
import ru.androidschool.intensiv.presentation.loadImage

class ArtistItem(
    private val content: CastVo
) : Item() {

    override fun getLayout() = R.layout.item_artist

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.artist_name.text = content.name
        viewHolder.artist_profile.loadImage(content.posterPath)
    }
}
