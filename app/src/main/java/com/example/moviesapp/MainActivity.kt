package com.example.moviesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.composables.MainPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        Handler(Looper.getMainLooper()).postDelayed({
//            parentFragmentManager.beginTransaction().replace(R.id.frameContainerView,
//                MainPageFragment()).addToBackStack("asddd").commit()
//
//        },3000)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
            MainPageFragment()
        ).commit()
    }
}