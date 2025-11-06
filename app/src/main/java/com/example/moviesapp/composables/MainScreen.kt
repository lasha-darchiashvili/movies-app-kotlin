package com.example.moviesapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.moviesapp.BottomNavigationItem
import com.example.moviesapp.Movie
import com.example.moviesapp.R
import com.example.moviesapp.navigation.Home
import com.example.moviesapp.navigation.MyNavigationHost
import com.example.moviesapp.navigation.Search
import com.example.moviesapp.navigation.WatchList
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.MainMoviesViewModel

const val IMAGE_URL_COMPOSE = "https://image.tmdb.org/t/p/original/"

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    println("$currentRoute")
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = painterResource(R.drawable.home1_clicked),
            unSelectedIcon = painterResource(R.drawable.home1)
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = painterResource(R.drawable.search1_clicked),
            unSelectedIcon = painterResource(R.drawable.search1)
        ),
        BottomNavigationItem(
            title = "Watchlist",
            selectedIcon = painterResource(R.drawable.bookmark1_clicked),
            unSelectedIcon = painterResource(R.drawable.bookmark1)
        )
    )
    var selectedItemIndex = when {
        currentRoute?.contains("Home") == true -> 0
        currentRoute?.contains("Search") == true -> 1
        currentRoute?.contains("WatchList") == true -> 2
        else -> -1
    }
    Scaffold(
        bottomBar = {
            // bad solution?
            if(currentRoute?.contains("MovieDetails") != true) {

                NavigationBar(
                    containerColor = Color(0xff242A32),
                    modifier = Modifier.drawBehind {
                        drawLine(
                            Color(0xff0296E5),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            ),
                            onClick = {
                                selectedItemIndex = index
                                when (index) {
                                    0 -> navController.navigate(Home)
                                    1 -> navController.navigate(Search)
                                    2 -> navController.navigate(WatchList)
                                }
                            },
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center

                                ) {
                                    Image(
                                        painter = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else {
                                            item.unSelectedIcon
                                        }, contentDescription = "image",
                                        modifier = Modifier.padding(bottom = 8.dp).size(20.dp)
                                    )
                                    Text(
                                        item.title, color = if (index == selectedItemIndex) {
                                            Color(0xff0296E5)
                                        } else {
                                            Color(0xff67686D)
                                        }
                                    )
                                }

                            },
                        )
                    }
                }
            }


        }
    ) { innerPadding ->
        MyNavigationHost(navController = navController, innerPadding = innerPadding)
    }
}


@Composable
fun Main(onMovieClick: (Int) -> Unit, innerPadding: PaddingValues) {
    Column(modifier = Modifier.background(Color(0xFF242A32)).padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier.fillMaxWidth(0.95f)) {
            Text(
                text = "What do you want to watch",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF)
            )
            Spacer(modifier = Modifier.height(22.dp))
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.height(42.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF3A3F47)),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(22.dp))
        Tabs(onMovieClick, innerPadding)

    }
}


@Composable
fun Tabs(onMovieClick: (Int) -> Unit, innerPadding: PaddingValues) {
    val tabItems = listOf(
        TabItem("popular"),
        TabItem("now_playing"),
        TabItem("top_rated"),
        TabItem("upcoming"),
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            edgePadding = 0.dp
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                        println(tabItems[index].title)
                    },
                    text = {
                        Text(text = item.title.replace('_', ' ')
                            .split(' ')
                            .joinToString(" ") { it.capitalize() } )
                    })
            }
        }
        println(tabItems[selectedTabIndex].title)
        MoviesList(tabItems[selectedTabIndex].title, onMovieClick, innerPadding)
    }
}

@Composable
fun MoviesList(category: String, onMovieClick: (Int) -> Unit, innerPadding: PaddingValues) {
    val viewModel: MainMoviesViewModel = hiltViewModel()
    LaunchedEffect(category) {
        viewModel.loadMovies(category)
    }
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is MainMoviesViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(top=30.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(
                    color = Color.Gray
                )
            }
        }

        is MainMoviesViewModel.ViewState.Error -> {
            println("eror")
        }

        is MainMoviesViewModel.ViewState.Success -> {
            val movies = (viewState as MainMoviesViewModel.ViewState.Success).data?.results ?: emptyList()
            println(movies)
            LazyGrid(movies, onMovieClick, innerPadding)

        }
    }

}

@Composable
fun LazyGrid(movies: List<Movie>, onMovieClick: (Int) -> Unit, innerPadding: PaddingValues){

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 20.dp,
            end = 12.dp,
            bottom = innerPadding.calculateBottomPadding() + 38.dp
        ),
        content = {
            items(movies) { movie ->
                Box(modifier = Modifier,
                    contentAlignment = Alignment.Center

                ){
                    AsyncImage(
                        model = movie.posterPath?.let { IMAGE_URL_COMPOSE + it } ?: "https://www.reelviews.net/resources/img/default_poster.jpg",
                        contentDescription = "movie image",
                        modifier = Modifier
                            .height(145.dp)

                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onMovieClick(movie.id)
                            }

                    )
                }

            }
        }
    )
}

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




@Composable
fun SingleMovieTest(movieId: Int) {
    Text("$movieId asdasdasdasd $movieId")
}

data class TabItem(val title: String)