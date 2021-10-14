package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_detail_bottom_view.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.GenresDto
import ru.androidschool.intensiv.data.MovieDetailsDto
import ru.androidschool.intensiv.data.ProductionCompanyDto
import ru.androidschool.intensiv.network.MovieDetailsApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.loadImage
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(FeedFragment.KEY_ID)
            Timber.i("show details for $movieId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()

        back_button.setOnClickListener { it ->
            activity?.supportFragmentManager?.popBackStack()
        }
        watch_button.setOnClickListener { it ->
            // TODO: implement logic
        }

        Timber.i("getMovieDetails for $movieId")
        val getDetails =
            MovieDetailsApiClient.apiClient.getMovieDetails(movieId)

        getDetails.enqueue(object : Callback<MovieDetailsDto> {
            override fun onFailure(call: Call<MovieDetailsDto>, e: Throwable) {
                Timber.e(e, "Failed get movie details")
            }

            override fun onResponse(
                call: Call<MovieDetailsDto>,
                response: Response<MovieDetailsDto>
            ) {
                val details = response.body()
                details?.let {
                    poster_image.loadImage(details.poster)
                    release.text = details.release
                    description.text = details.overview
                    movie_rating.rating = details.rating
                    movie_name.text = details.title
                    production.text =
                        details.productionCompanies.map(ProductionCompanyDto::name).joinToString()
                    genres.text = details.genres.map(GenresDto::name).joinToString()

                    // TODO: get state from provider or shared preferences
                    favorite.setChecked(true)
                    favorite.setOnCheckedChangeListener { buttonView, isChecked ->
                        // TODO: save value
                    }
                    val cast = details.credits.cast
                    cast.let {
                        val artistList = cast.map {
                            ArtistItem(it)
                        }.toList()
                        artists_recycler_view.adapter = adapter.apply { addAll(artistList) }
                    }
                }
            }
        })
    }
}
