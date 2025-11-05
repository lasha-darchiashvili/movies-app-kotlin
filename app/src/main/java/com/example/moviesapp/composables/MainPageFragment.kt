package com.example.moviesapp.composables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.moviesapp.Movie
import com.example.moviesapp.databinding.FragmentMainBinding
import com.example.moviesapp.navigation.MyNavigationHost
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.MainMoviesViewModel
import com.example.moviesapp.viewmodels.MoviesViewModelFactory

//const val IMAGE_URL_COMPOSE = "https://image.tmdb.org/t/p/original/"

class MainPageFragment: Fragment(){
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val composeView = ComposeView(requireContext())

        composeView.setContent{
//            MyNavigationHost()
        }
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        binding.apply{
//
//            val adapter = MyFragmentAdapter(parentFragmentManager, lifecycle)
//            viewPager.adapter = adapter
//
//            TabLayoutMediator(tab, viewPager) { tab, position ->
//                when(position) {
//                    0 -> tab.text = "Now Playing"
//                    1-> tab.text = "Popular"
//                    2-> tab.text = "Top Rated"
//                    3 -> tab.text = "Upcoming"
//                }
//            }.attach()
//
//
//        }
    }


}
//
//@Composable
//fun Main(onMovieClick: (Int) -> Unit) {
//    Box(
//    modifier = Modifier
//        .fillMaxSize()
//        .background(Color(0xFF242A32))
//        .padding(top = 30.dp),
//    contentAlignment = Alignment.Center
//) {
//    Column(modifier = Modifier.fillMaxSize(0.95f)) {
//        Text(
//            text = "What do you want to watch",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFFFFFFFF)
//        )
//        Spacer(modifier = Modifier.height(22.dp))
//        TextField(
//            value = "",
//            onValueChange = {},
//            modifier = Modifier.height(42.dp)
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(12.dp))
//                .background(Color(0xFF3A3F47)),
//            singleLine = true
//        )
//        Spacer(modifier = Modifier.height(22.dp))
//        Tabs(onMovieClick)
//
//    }
//
//
//}
//}
//
//
//@Composable
//fun Tabs(onMovieClick: (Int) -> Unit) {
//    val tabItems = listOf(
//        TabItem("popular"),
//        TabItem("now_playing"),
//        TabItem("top_rated"),
//        TabItem("upcoming"),
//    )
//    var selectedTabIndex by remember {
//        mutableIntStateOf(0)
//    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        TabRow(selectedTabIndex = selectedTabIndex,
//            backgroundColor = Color.Transparent,
//            contentColor = Color.White,
//            ) {
//            tabItems.forEachIndexed { index, item ->
//                Tab(selected = index == selectedTabIndex,
//                    onClick = {
//                        selectedTabIndex = index
//                        println(tabItems[index].title)
//                    },
//                    text = {
//                        Text(text = item.title.replace('_', ' ')
//                            .split(' ')
//                            .joinToString(" ") { it.capitalize() } )
//                    })
//            }
//        }
//        println(tabItems[selectedTabIndex].title)
//    MoviesList(tabItems[selectedTabIndex].title, onMovieClick)
//    }
//}
//
//@Composable
//fun MoviesList(category: String, onMovieClick: (Int) -> Unit) {
//    val viewModel: MainMoviesViewModel = viewModel(key = category, factory = MoviesViewModelFactory(MoviesRepositoryImpl(), category)
//    )
//    val viewState by viewModel.viewState.collectAsState()
//
//    when (viewState) {
//        is MainMoviesViewModel.ViewState.Loading -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    color = Color.Gray
//                )
//            }
//        }
//
//        is MainMoviesViewModel.ViewState.Error -> {
//            println("eror")
//        }
//
//        is MainMoviesViewModel.ViewState.Success -> {
//            val movies = (viewState as MainMoviesViewModel.ViewState.Success).data?.results ?: emptyList()
//            println(movies)
//            LazyGrid(movies, onMovieClick)
//
//        }
//    }
//
//}
//
//@Composable
//fun LazyGrid(movies: List<Movie>, onMovieClick: (Int) -> Unit){
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//
//        contentPadding = PaddingValues(
//            start = 12.dp,
//            top = 16.dp,
//            end = 12.dp,
//            bottom = 16.dp
//        ),
//        content = {
//            items(movies) { movie ->
//                AsyncImage(
//                    model = movie.posterPath?.let {IMAGE_URL_COMPOSE + it } ?: "https://www.reelviews.net/resources/img/default_poster.jpg",
//                    contentDescription = "movie image",
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .fillMaxWidth()
//                        .aspectRatio(1f)
//                        .height(120.dp)
//                        .clickable {
//                            onMovieClick(movie.id)
//                        }
//                )
//            }
//        }
//    )
//}

//@Composable
//fun UserProfile(name: String, url: String) {
//    var counter by rememberSaveable {
//        mutableIntStateOf(0)
//    }
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Image(modifier = Modifier.size(10.dp),
//             painter = painterResource(R.drawable.baseline_mood_24),
//             contentDescription = null
//            )
//        Text(modifier = Modifier.padding(top=20.dp),
//            text = counter.toString())
//        Button(onClick ={counter++} ) {
//            Text("+")
//        }
//    }
//}




//@Composable
//fun SingleMovieTest(movieId: Int) {
//    Text("$movieId asdasdasdasd $movieId")
//}
//
//data class TabItem(val title: String)
