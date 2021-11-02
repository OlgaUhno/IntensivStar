package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.progress_indicator.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.data.MoviesResponseDto
import ru.androidschool.intensiv.database.MovieDao
import ru.androidschool.intensiv.database.MovieDatabase
import ru.androidschool.intensiv.database.entities.Movie
import ru.androidschool.intensiv.database.relations.MoviesWithCategory
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.rx.*
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.ui.navigationOptions
import ru.androidschool.intensiv.util.Constants
import ru.androidschool.intensiv.util.MovieCategories
import ru.androidschool.intensiv.util.MoviesWithCategoryToMovieDtoConverter
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FeedFragment created")
        adapter.clear()

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            getOfflineFirstObservable()
                .subscribe({ result ->
                    setResults(result)
                }, { error -> Timber.e(error, "Failed get movies") })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun prepareSource(source: Single<MoviesResponseDto>) =
        source.compose(addSingleSchedulers())

    private fun setResults(result: HashMap<MovieCategories, List<MovieDto>>) {
        val resultsList: List<MainCardContainer> =
            result.entries.map { toCardContainer(it.value, getCardTitleResId(it.key)) }
        movies_recycler_view.adapter =
            adapter.apply { addAll(resultsList) }
        // saveData(result)
    }

    private fun getCardTitleResId(category: MovieCategories): Int {
        return when (category) {
            MovieCategories.TOP -> R.string.recommended
            MovieCategories.NOW -> R.string.now_playing
            MovieCategories.UPCOMING -> R.string.upcoming
        }
    }

    private fun toCardContainer(
        result: List<MovieDto>,
        @StringRes title: Int
    ): MainCardContainer {
        return MainCardContainer(
            title,
            result.map {
                MovieItem(it) { movie ->
                    openMovieDetails(movie)
                }
            }.toList()
        )
    }

    private fun toMovie(
        result: List<MovieDto>,
        category: MovieCategories
    ): List<Movie> {
        return result.map {
            Movie(
                movieId = it.id,
                posterPath = it.posterPath,
                title = it.title,
                voteAverage = it.voteAverage,
                category = category.type
            )
        }.toList()
    }

    private fun openMovieDetails(movie: MovieDto) {
        val bundle = Bundle()
        // TODO: change to use safe arguments
        bundle.putInt(Constants.KEY_ID, movie.id ?: -1)
        findNavController().navigate(R.id.movie_details_fragment, bundle, navigationOptions)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, navigationOptions)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    fun getOfflineFirstObservable(): Flowable<HashMap<MovieCategories, List<MovieDto>>> {
        val remoteObservable = createRemoteSingleObservable()

        return createOfflineSingleObservable()
            .onErrorResumeNext(remoteObservable)
            .concatWith(remoteObservable)
            .compose(addFlowableSchedulers())
    }

    private fun createRemoteSingleObservable(): Single<HashMap<MovieCategories, List<MovieDto>>> {
        val playNowSource = prepareSource(MovieApiClient.apiClient.getNowPlayingMovies())
        val topRatedSource = prepareSource(MovieApiClient.apiClient.getTopRatedMovies())
        val upcomingSource = prepareSource(MovieApiClient.apiClient.getUpcomingMovies())

        return Single.zip(
            playNowSource, topRatedSource, upcomingSource,
            Function3<MoviesResponseDto, MoviesResponseDto, MoviesResponseDto, HashMap<MovieCategories, List<MovieDto>>> { now, top, upcoming ->
                hashMapOf(
                    MovieCategories.TOP to top.results,
                    MovieCategories.NOW to now.results,
                    MovieCategories.UPCOMING to upcoming.results
                )
            }
        )
            .doOnSuccess { results: HashMap<MovieCategories, List<MovieDto>> -> saveData(results) }
    }

    private fun createOfflineSingleObservable(): Single<HashMap<MovieCategories, List<MovieDto>>> {
        val db: MovieDao = MovieDatabase.get(requireContext()).movieCategoryDao()

        return db.getCategoiesyWithMovies()
            .map { result: List<MoviesWithCategory> ->
                val hashMap = HashMap<MovieCategories, List<MovieDto>>()
                result.map {
                    val moviesList: List<MovieDto> =
                        MoviesWithCategoryToMovieDtoConverter.toMovieDto(it.movies)
                    val category: MovieCategories? =
                        MovieCategories.fromInt(it.movieCategory.category)
                    hashMap.put(category ?: MovieCategories.TOP, moviesList)
                }
                hashMap
            }
            .compose(addSingleProgress(progress_bar))
    }

    private fun saveData(result: HashMap<MovieCategories, List<MovieDto>>) {
        val cashedList: List<Movie> = result.entries.flatMap { toMovie(it.value, it.key) }

        val db: MovieDao = MovieDatabase.get(requireContext()).movieCategoryDao()
        db.deleteAll()
            .concatWith(db.save(cashedList))
            .compose(addCompletableSchedulers()).subscribe({
                Timber.i("Movies have been saved to database")
            }, { error ->
                Timber.e(error, "Failed save movies to database")
            })
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
    }
}
