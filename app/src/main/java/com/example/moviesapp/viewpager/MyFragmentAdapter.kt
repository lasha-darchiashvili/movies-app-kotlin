package com.example.moviesapp.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesapp.MoviesListFragment

class MyFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                return MoviesListFragment.newInstance("now_playing")
            }
            1 -> {
                return MoviesListFragment.newInstance("popular")
            }
            2 -> {
                return MoviesListFragment.newInstance("top_rated")
            }
            3 -> {
                return MoviesListFragment.newInstance("upcoming")
            }
            else -> return MoviesListFragment.newInstance("now_playing")
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}
