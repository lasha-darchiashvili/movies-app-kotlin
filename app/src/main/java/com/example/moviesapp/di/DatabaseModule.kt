package com.example.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.example.moviesapp.Daos.MoviesDao
import com.example.moviesapp.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            com.example.moviesapp.database.AppDatabase::class.java,
            "movie_database"
        )
            .build()
    }

    @Provides
    fun provideMovieDao(db: AppDatabase ): MoviesDao {
        return db.moviesDao()
    }
}