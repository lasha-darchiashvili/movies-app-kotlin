package com.example.moviesapp.app

import android.app.Application
import com.example.moviesapp.database.AppDatabase


class App: Application() {
    val dataBase : AppDatabase by lazy {
        AppDatabase.getDatabase(context = this)
    }
}