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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.MoviesRecycle.MoviesAdapter
import com.example.moviesapp.databinding.FragmentMoviesBinding
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.MainMoviesViewModel
import kotlinx.coroutines.launch

const val CATEGORY = "category"



class MoviesListFragment: Fragment() {
    private var _binding: FragmentMoviesBinding? = null


//    private val viewModel: MainMoviesViewModel by viewModels {
//        MoviesViewModelFactory(MoviesRepositoryImpl(arguments?.getString(CATEGORY)!!))
//    }
    val binding get() = _binding!!

    val moviesAdapter = MoviesAdapter(onClick = { movieData ->
        parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
            MovieFragment.newInstance(movieData)).commit()
    })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.apply{
//            recyclerView.layoutManager = GridLayoutManager(context, 3)
//            recyclerView.adapter = moviesAdapter
//            viewLifecycleOwner.lifecycleScope.launch {
//                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.viewState.collect { viewState ->
//                        when (viewState) {
//                            is MainMoviesViewModel.ViewState.Success -> {
//                                loader.isVisible = false
//                                viewState.data?.results?.let {
//                                    moviesAdapter.submitList(it)
//                                    }
//
//                            }
//                            is MainMoviesViewModel.ViewState.Error -> {
//                                loader.isVisible = false
//                                println("error")
//                            }
//                            is MainMoviesViewModel.ViewState.Loading -> {
//                                loader.isVisible = true
//                            }
//                        }
//
//                    }
//                }
//            }
//
//
//
//
//        }
    }

    companion object {
        fun newInstance(category: String): MoviesListFragment {
            return MoviesListFragment().apply {
                val bundle = Bundle()
                bundle.putString(CATEGORY, category)
                arguments = bundle
            }

        }
    }
}