package ru.androidschool.intensiv.util

enum class MovieCategories(val type: Int) {
    TOP(0),
    NOW(1),
    UPCOMING(2);

    companion object {
        private val map = MovieCategories.values().associateBy(MovieCategories::type)
        fun fromInt(type: Int) = map[type]
    }
}
