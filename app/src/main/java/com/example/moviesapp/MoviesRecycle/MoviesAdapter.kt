package com.example.moviesapp.MoviesRecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.Movie
import com.example.moviesapp.R

const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"

class MoviesAdapter( private val onClick: (Movie) -> Unit): RecyclerView.Adapter<MovieViewHolder>() {
    private val movieData = mutableListOf<Movie>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movieData[position]
            holder.itemView.findViewById<ImageView>(R.id.moviePoster).load(IMAGE_URL + movie.posterPath)
            holder.itemView.setOnClickListener { onClick.invoke(movie) }

    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    fun submitList(movieData: List<Movie>) {
        this.movieData.clear()
        this.movieData.addAll(movieData)
        notifyDataSetChanged()
    }
}