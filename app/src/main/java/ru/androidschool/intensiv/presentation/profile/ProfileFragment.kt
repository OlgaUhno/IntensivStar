package ru.androidschool.intensiv.presentation.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.FavoritesMoviesRepository
import ru.androidschool.intensiv.domain.usecase.FavoritesMoviesUseCase
import ru.androidschool.intensiv.presentation.favoriteslist.FavoritesListFragment
import ru.androidschool.intensiv.presentation.watchlist.WatchlistFragment
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileTabLayoutTitles: Array<String>

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(
                requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(avatar)

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)

        val profileAdapter = ProfileAdapter(
            this
        )

        profileAdapter.addFragment(FavoritesListFragment.newInstance())
        profileAdapter.addFragment(WatchlistFragment.newInstance())

        doppelgangerViewPager.adapter = profileAdapter

        doppelgangerViewPager.registerOnPageChangeCallback(profilePageChangeCallback)

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->

            // Выделение первой части заголовка таба
            // Название таба
            var title = profileTabLayoutTitles[position]
            if (position == 0) {
                title = String.format(title, 0)
            }
            updateTitle(title, tab)
        }.attach()

        FavoritesMoviesUseCase(FavoritesMoviesRepository()).getMovies(requireContext())
            .subscribe({ result ->
                val tab = tabLayout.getTabAt(0)

                tab?.let {
                    var title = profileTabLayoutTitles[0]
                    title = String.format(title, result.size)

                    updateTitle(title, tab)
                }
            }, { error -> Timber.e(error, "Failed get movies ") })
    }

    private fun updateTitle(title: String, tab: TabLayout.Tab) {
        // Раздеряем название на части. Первый элемент будет кол-во
        val parts = title.split(" ")
        var number = parts[0].count()
        val spannableStringTitle = SpannableString(title)
        spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number, 0)

        tab.text = spannableStringTitle
    }
}
