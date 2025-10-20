package com.example.moviesapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.Movie
import com.example.moviesapp.R
import com.example.moviesapp.Review
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.ReviewsViewModel
import com.example.moviesapp.viewmodels.ReviewsViewModelFactory
import com.example.moviesapp.viewmodels.SimilarMoviesViewModel
import com.example.moviesapp.viewmodels.SimilarMoviesViewModelFactory
import com.example.moviesapp.viewmodels.SingleMovieViewModel
import com.example.moviesapp.viewmodels.SingleMovieViewModelFactory

@Composable
fun SingleMovieComp(movieId: Int, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF242A32)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header(navController)
        MovieMainSectionLoader(movieId)
        TabsDetailsPage(movieId)
    }

}

@Composable
fun Header(navController: NavController) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.95f).padding(top = 30.dp)


        ){
        Image(painter = painterResource(R.drawable.back),
            contentDescription = null,

            modifier = Modifier.size(width = 20.dp, height = 20.dp).clickable{
                navController.popBackStack()
            })

        Text(text = "Details", fontSize = 16.sp, fontWeight = FontWeight.W600, color = Color(0xFFFFFFFF))

        Image(painter = painterResource(R.drawable.clock),
            contentDescription = null,
            modifier = Modifier.size(width = 20.dp, height = 20.dp))
    }

}

@Composable
fun MovieMainSectionLoader(movieId: Int) {
    val viewModel: SingleMovieViewModel = viewModel(key=movieId.toString(),
        factory = SingleMovieViewModelFactory(MoviesRepositoryImpl(movieId = movieId))
    )
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is SingleMovieViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Gray
                )
            }
        }

        is SingleMovieViewModel.ViewState.Error -> {
            println("eror")
        }

        is SingleMovieViewModel.ViewState.Success -> {
            val movie = (viewState as SingleMovieViewModel.ViewState.Success).data
            if (movie != null) {
                println(movie)
                MovieMainSection(movie)
            }

        }
    }
}

@Composable
fun MovieMainSection(movie: Movie) {

    Box() {
        AsyncImage(
            model = IMAGE_URL_COMPOSE + movie.backdropPath,
            contentDescription = "movie image",
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(top = 28.dp),
            contentScale = ContentScale.FillWidth
        )
        Row(modifier = Modifier.padding(start = 29.dp, top = 151.dp),
            verticalAlignment = Alignment.Bottom) {
            AsyncImage(
                model = IMAGE_URL_COMPOSE + movie.posterPath,
                contentDescription = "movie image",
                modifier = Modifier
                    .height(120.dp).width(95.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillHeight

            )
            Text(text = movie.title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 12.dp))

        }
    }

}

@Composable
fun TabsDetailsPage(movieId: Int) {
    val tabItems = listOf(
        TabItem("reviews"),
        TabItem("similar"),
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
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
        if(selectedTabIndex == 0) {
            ReviewsTabsContentLoader("reviews", movieId)

        }
        else{
            SimilarMovieTabsContentLoader("similar", movieId)
        }

    }
}

@Composable
fun SimilarMovieTabsContentLoader(contentType: String, movieId: Int) {
    val viewModel: SimilarMoviesViewModel = viewModel(key="$contentType $movieId",
        factory = SimilarMoviesViewModelFactory(
            MoviesRepositoryImpl(
                contentType,
                similarMovieId = movieId
            )
        )
    )
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is SimilarMoviesViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Gray
                )
            }
        }

        is SimilarMoviesViewModel.ViewState.Error -> {
            println("error")
        }

        is SimilarMoviesViewModel.ViewState.Success -> {
            val similarMovies = (viewState as SimilarMoviesViewModel.ViewState.Success).data?.results ?: emptyList()
            println(similarMovies)
            LazyGridSimilarMovies(similarMovies)

        }
    }
}


@Composable
fun ReviewsTabsContentLoader(contentType: String, movieId: Int) {
    val viewModel: ReviewsViewModel = viewModel(key="$contentType $movieId",
        factory = ReviewsViewModelFactory(
            MoviesRepositoryImpl(
                contentType,
                similarMovieId = movieId
            )
        )
    )
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is ReviewsViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Gray
                )
            }
        }

        is ReviewsViewModel.ViewState.Error -> {
            println("error")
        }

        is ReviewsViewModel.ViewState.Success -> {
            val reviews = (viewState as ReviewsViewModel.ViewState.Success).data?.results ?: emptyList()
            println(reviews)

            LazyGridReviews(reviews)


        }
    }
}

@Composable
fun LazyGridSimilarMovies(movies: List<Movie>){

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(movies) { movie ->
                AsyncImage(
                    model = IMAGE_URL_COMPOSE + movie.posterPath,
                    contentDescription = "movie image",
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .height(120.dp)
                )
            }
        }
    )
}

@Composable
fun LazyGridReviews(reviews: List<Review>){

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(reviews) { review ->
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp)
                ) {
                    Text(
                        text = review.author,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = review.content,
                        color = Color.White,
                        fontSize = 13.sp,
                        maxLines = 4
                    )
                }
            }
        }
    )
}


