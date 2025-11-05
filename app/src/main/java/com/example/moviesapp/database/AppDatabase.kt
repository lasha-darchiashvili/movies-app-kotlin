package com.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.Daos.MoviesDao
import com.example.moviesapp.entitites.MovieItem

@Database(entities = [MovieItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun toDosDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: com.example.moviesapp.database.AppDatabase? = null

        fun getDatabase(context: Context): com.example.moviesapp.database.AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.moviesapp.database.AppDatabase::class.java,
                    "movie_database"
                )
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}