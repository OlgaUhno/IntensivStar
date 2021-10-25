package ru.androidschool.intensiv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.database.entities.Movie
import ru.androidschool.intensiv.database.entities.MovieCategory

@Database(entities = [Movie::class, MovieCategory::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieCategoryDao(): MovieDao

    companion object {
        private var instance: MovieDatabase? = null

        @Synchronized
        fun get(context: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "MovieDatabase"
                ).build()
            }
            return instance!!
        }
    }
}
