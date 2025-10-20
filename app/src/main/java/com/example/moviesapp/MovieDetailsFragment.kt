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
import com.example.moviesapp.MoviesRecycle.DetailsAdapter
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.DetailsViewModel
import com.example.moviesapp.viewmodels.DetailsViewModelFactory
import kotlinx.coroutines.launch
import kotlin.getValue

class MovieDetailsFragment: Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(MoviesRepositoryImpl(movieId = arguments?.getInt("movieId")!!))
//            ,detailsTabName = arguments?.getString("detailsTabName"))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply{
            recyclerView.layoutManager = GridLayoutManager(context, 2)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect { viewState ->
                        when (viewState) {
                            is DetailsViewModel.ViewState.Success -> {
                                loader.isVisible = false
                                println(1)
                                println(viewState.data?.results)
                                viewState.data?.results?.let {
                                    recyclerView.adapter = DetailsAdapter(it)
                                }
                            }
                            is DetailsViewModel.ViewState.Error -> {
                                loader.isVisible = false
                                println("error")
                            }
                            is DetailsViewModel.ViewState.Loading -> {
                                loader.isVisible = true
                            }
                        }

                    }
                }
            }
            }
    }

    companion object {
        fun newInstance(movieId: Int, detailsTabName: String): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                val bundle = Bundle()
                bundle.putInt("movieId", movieId)
                bundle.putString(detailsTabName, "detailsTabName")
                arguments = bundle
            }

        }
    }
}