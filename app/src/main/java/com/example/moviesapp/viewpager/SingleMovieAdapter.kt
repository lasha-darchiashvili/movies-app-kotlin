package com.example.moviesapp.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesapp.MovieDetailsFragment
import com.example.moviesapp.MoviesListFragment

class SingleMovieAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val movieId: Int
): FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                return MovieDetailsFragment.newInstance(movieId, "Similar movies")
            }
            1 -> {
                return MovieDetailsFragment.newInstance(movieId, "Reviews")
            }

            else -> return MovieDetailsFragment.newInstance(movieId, "Similar movies")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
