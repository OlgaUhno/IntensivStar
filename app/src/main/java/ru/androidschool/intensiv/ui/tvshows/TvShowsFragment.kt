package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShowsResponse
import ru.androidschool.intensiv.network.TvShowApiClient
import ru.androidschool.intensiv.util.Constants
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.clear()

        val getPopularShows =
            TvShowApiClient.apiClient.getPopularShows(Constants.API_KEY, Constants.LANGUAGE)

        getPopularShows.enqueue(object : Callback<TvShowsResponse> {
            override fun onFailure(call: Call<TvShowsResponse>, e: Throwable) {
                Timber.e(e, "Failed get popular shows")
            }

            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                val shows = response.body()?.results
                shows?.let {
                    val newShowsList = shows.map {

                        TvShowItem(it) { show -> }
                    }.toList()

                    tvShows_recycler_view.adapter = adapter.apply { addAll(newShowsList) }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = TvShowsFragment()
    }
}
