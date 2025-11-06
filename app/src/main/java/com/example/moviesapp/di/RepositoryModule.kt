package com.example.moviesapp.di

import com.example.moviesapp.repository.MoviesRepository
import com.example.moviesapp.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository
}