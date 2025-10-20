package com.example.moviesapp.MoviesRecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.Movie
import com.example.moviesapp.R


class DetailsAdapter(private val movieData: List<Movie>): RecyclerView.Adapter<DetailsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsViewHolder {
        return DetailsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_detail, parent, false))
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val movie = movieData[position]
        holder.itemView.findViewById<ImageView>(R.id.moviePoster).load(IMAGE_URL + movie.posterPath)
    }

    override fun getItemCount(): Int {
        println{"asd${movieData.size}"}
        return movieData.size
    }
}