package com.example.moviesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.moviesapp.composables.MainPageFragment
import com.example.moviesapp.databinding.FragmentMovieBinding
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.SingleMovieViewModel
import com.example.moviesapp.viewmodels.SingleMovieViewModelFactory
import com.example.moviesapp.viewpager.SingleMovieAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import kotlin.getValue

const val IMAGE_URL = "https://image.tmdb.org/t/p/original"

class MovieFragment: Fragment() {
    private var _binding: FragmentMovieBinding? = null
    val binding get() = _binding!!

//    private val viewModel: SingleMovieViewModel by viewModels {
//        SingleMovieViewModelFactory(MoviesRepositoryImpl(), movieId = arguments?.getInt("movieId")!!)
//    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.apply{
//            val movieId = arguments?.getInt("movieId")!!
//            println(movieId)
//
//            val adapter = SingleMovieAdapter(parentFragmentManager, lifecycle, arguments?.getInt("movieId")!!)
//            viewPager.adapter = adapter
//
//            TabLayoutMediator(tab, viewPager) { tab, position ->
//                when(position) {
//                    0 -> tab.text = "Similar movies"
//                    1-> tab.text = "Reviews"
//                }
//            }.attach()
//
//            viewLifecycleOwner.lifecycleScope.launch {
//                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.viewState.collect { viewState ->
//                        when (viewState) {
//                            is SingleMovieViewModel.ViewState.Success -> {
//                                loader.isVisible = false
//                                viewState.data?.let {
//                                    movieTitle.text = it.title
//                                    backDrop.load(IMAGE_URL + it.backdropPath)
//                                    moviePoster.load(IMAGE_URL + it.posterPath)
//                                    loader.visibility = View.GONE
//                                }
//                            }
//                            is SingleMovieViewModel.ViewState.Error -> {
//                                loader.isVisible = false
//                                println("error")
//                            }
//                            is SingleMovieViewModel.ViewState.Loading -> {
//                                loader.isVisible = true
//                            }
//                        }
//
//                    }
//                }
//            }
//
//            backIcon.setOnClickListener {
//                parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
//                    MainPageFragment()
//                ).commit()
//            }
//
//
//        }
    }


    companion object {
        fun newInstance(movie: Movie): MovieFragment {
            return MovieFragment().apply {
                val bundle = Bundle()
                bundle.putInt("movieId", movie.id )
                arguments = bundle
            }

        }
    }

}