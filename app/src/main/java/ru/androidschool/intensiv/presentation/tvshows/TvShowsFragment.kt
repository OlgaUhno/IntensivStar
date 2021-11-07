package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.progress_indicator.*
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.TvShowsRemoteRepository
import ru.androidschool.intensiv.domain.usecase.TvShowsUseCase
import ru.androidschool.intensiv.domain.usecase.addSingleProgress
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.clear()

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            TvShowsUseCase(TvShowsRemoteRepository()).getShows()
                .compose(
                    addSingleProgress(
                        progress_bar
                    )
                )
                .subscribe({ result ->
                    val newShowsList = result.map {
                        TvShowItem(it) { show -> }
                    }.toList()

                    tvShows_recycler_view.adapter = adapter.apply { addAll(newShowsList) }
                }, { error -> Timber.e(error, "Failed get popular shows") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TvShowsFragment()
    }
}
