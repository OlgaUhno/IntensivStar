package ru.androidschool.intensiv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.data.database.entities.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): FavoriteMovieDao

    companion object {
        private var instance: FavoriteMoviesDatabase? = null

        @Synchronized
        fun get(context: Context): FavoriteMoviesDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteMoviesDatabase::class.java, "FavoriteMoviesDatabase"
                ).build()
            }
            return instance!!
        }
    }
}
