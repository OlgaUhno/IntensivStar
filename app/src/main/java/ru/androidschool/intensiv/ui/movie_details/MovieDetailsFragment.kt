package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_detail_bottom_view.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository

private const val ARG_PARAM1 = "param1"

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artists_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        back_button.setOnClickListener { it ->
            //TODO: implement back button
        }

        val details = MockRepository.getMovieDetauls(title)
        Picasso.get()
            .load(details.posterPath)
            .into(poster_image)
        production.text = details.productionCompanies.joinToString()
        genres.text = details.genres.joinToString()
        release.text = details.release
        description.text = details.overview
        movie_rating.rating = details.rating
        movie_name.text = details.title
        // TODO: get state from provider or shared preferences
        favorite.setChecked(true)
        favorite.setOnCheckedChangeListener { buttonView, isChecked ->
            // TODO: save value
        }
        watch_button.setOnClickListener { it ->
            // TODO: implement logic
        }
        val artistList = details.cast.map {
            ArtistItem(it)
        }.toList()

        artists_recycler_view.adapter = adapter.apply { addAll(artistList) }
    }
}
