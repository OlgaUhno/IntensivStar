package ru.androidschool.intensiv.ui.tvshows

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.item_tv_show.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShowDto
import ru.androidschool.intensiv.ui.loadImage
import ru.androidschool.intensiv.util.getFullPosterPath
import ru.androidschool.intensiv.util.getRating

class TvShowItem(
    private val content: TvShowDto,
    private val onClick: (tvShow: TvShowDto) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_tv_show

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.tv_show_name.text = content.name
        viewHolder.tv_show_rating.rating = getRating(content.voteAverage)
        viewHolder.tv_show_content.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.cover_image.loadImage(getFullPosterPath(content.posterPath))
    }
}
