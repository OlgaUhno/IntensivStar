package ru.androidschool.intensiv.presentation.tvshows

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.item_tv_show.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.loadImage
import ru.androidschool.intensiv.data.vo.TvShowVo

class TvShowItem(
    private val content: TvShowVo,
    private val onClick: (tvShow: TvShowVo) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_tv_show

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.tv_show_name.text = content.name
        viewHolder.tv_show_rating.rating = content.rating
        viewHolder.tv_show_content.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.cover_image.loadImage(content.posterPath)
    }
}
