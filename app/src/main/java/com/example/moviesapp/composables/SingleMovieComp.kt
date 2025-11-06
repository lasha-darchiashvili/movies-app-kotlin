package com.example.moviesapp.composables

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieDetails
import com.example.moviesapp.R
import com.example.moviesapp.Review
import com.example.moviesapp.app.App
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.MoviesDatabaseViewModel
import com.example.moviesapp.viewmodels.MoviesDatabaseViewModelFactory
import com.example.moviesapp.viewmodels.ReviewsViewModel
import com.example.moviesapp.viewmodels.SimilarMoviesViewModel
import com.example.moviesapp.viewmodels.SingleMovieViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SingleMovieComp(movieId: Int, navController: NavController) {

    var movieOverview by remember {
        mutableStateOf<String?>(null)
    }
    var movieDetails by remember {
        mutableStateOf<MovieDetails?>(null)
    }

    val context = LocalContext.current
//    context.deleteDatabase("movie_database")
    val viewModel: MoviesDatabaseViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    val movieList = viewState.movies
    val movieAlreadyExists = movieList.map{item -> item.title}.contains(movieDetails?.title)



    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF242A32)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header({ navController.popBackStack() }, "Details", R.drawable.bookmark_filled, movieDetails, viewModel, movieAlreadyExists)
        MovieMainSectionLoader(movieId, { overview ->
            movieOverview = overview
        },) {details ->
            movieDetails = details
    }
        Spacer(modifier = Modifier.height(30.dp))
        TabsDetailsPage(movieId, movieOverview)
    }

}

@Composable
fun Header(onNavigateBack: () -> Unit, title: String, rightSideIcon: Int? = null, movieDetails: MovieDetails? = null,
           viewModel: MoviesDatabaseViewModel? = null, movieAlreadyExists: Boolean? = null) {

    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.88f).padding(top = 45.dp)


        ){
        Image(painter = painterResource(R.drawable.back),
            contentDescription = null,

            modifier = Modifier.size(width = 20.dp, height = 20.dp).clickable{
                onNavigateBack()
            })

        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.W600, color = Color(0xFFFFFFFF))

        if(rightSideIcon != null) {
            Image(painter = painterResource(rightSideIcon),
                contentDescription = null,
                modifier = Modifier.size(width = 20.dp, height = 20.dp).clickable{
                    if(movieDetails != null && movieAlreadyExists == false){
                        viewModel?.insertIntoDb(movieDetails.title ?: "Unknown",
                            movieDetails.voteAverage ?: 0.00,
                            releaseDate = movieDetails.releaseDate ?: "Unknown",
                            poster = movieDetails.posterPath ?: "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg" )
                    }
                })
        }
        else {
            Text("")
        }

    }

}

@Composable
fun MovieMainSectionLoader(movieId: Int, setOverview: (String?) -> Unit, setMovieDetails: (MovieDetails) -> Unit) {
    val viewModel: SingleMovieViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
//    LaunchedEffect(
//        Unit
//    ) {
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            Log.d("Lasha", "asd")
//        }
//    }

    when (viewState) {
        is SingleMovieViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(top=30.dp),
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
                setOverview(movie.overview)
                setMovieDetails(movie)
            }

        }
    }
}

@Composable
fun MovieMainSection(movie: MovieDetails) {
    Column() {
        Box() {
            AsyncImage(
                model = movie.backdropPath?.let {IMAGE_URL_COMPOSE + it } ?: "https://img.freepik.com/free-photo/movie-background-collage_23-2149876028.jpg?semt=ais_hybrid&w=740&q=80",
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
                    model = movie.posterPath?.let {IMAGE_URL_COMPOSE + it } ?: "https://www.reelviews.net/resources/img/default_poster.jpg",
                    contentDescription = "movie image",
                    modifier = Modifier
                        .height(120.dp).clip(RoundedCornerShape(16.dp)),

                    )
                Text(text = movie.title ?: "Title", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(start = 12.dp))

            }
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(top=24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.calendar2), contentDescription = "calendar",
                    modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(fontSize=12.sp, fontWeight = FontWeight.W500,
                    color = Color(0xff696974),
                    text=if (!movie.releaseDate.isNullOrEmpty() && movie.releaseDate.length >= 4) {
                        movie.releaseDate.substring(0, 4)
                    } else {
                        "unknown"
                    })
            }

            Spacer(modifier = Modifier.width(12.dp))
            Text(text="|", color = Color(0xff696974))
            Spacer(modifier = Modifier.width(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.clock2), contentDescription = "clock",
                    modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(fontSize=12.sp, fontWeight = FontWeight.W500,
                    color = Color(0xff696974),
                    text=if(movie.runtime.toString().isNotEmpty()) movie.runtime.toString() + " Minutes" else "unknown")
            }

            Spacer(modifier = Modifier.width(12.dp))
            Text(text="|", color = Color(0xff696974))
            Spacer(modifier = Modifier.width(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.genre), contentDescription = "genre",
                    modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(fontSize=12.sp, fontWeight = FontWeight.W500,
                    color = Color(0xff696974),
                    text=if(movie.genres != null) movie.genres[0].name else "unknown")
            }
        }
    }

}

@Composable
fun TabsDetailsPage(movieId: Int, movieOverview: String?) {
    val tabItems = listOf(
        TabItem("about_movie"),
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
        if (selectedTabIndex == 0){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AboutMovie(movieOverview)
            }
        }
        else if(selectedTabIndex == 1) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ReviewsTabsContentLoader(movieId)
            }
        }
        else{
            SimilarMovieTabsContentLoader(movieId)
        }

    }
}
@Composable
fun AboutMovie(overview: String?) {
    Text(text = overview ?: "No overview", fontSize=12.sp, fontWeight = FontWeight.W400,
        color= Color(0xffFFFFFF), modifier = Modifier.fillMaxWidth(0.84f).padding(top = 24.dp))
}

@Composable
fun SimilarMovieTabsContentLoader(movieId: Int) {
    val viewModel: SimilarMoviesViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is SimilarMoviesViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(top=30.dp),
                contentAlignment = Alignment.TopCenter
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
fun ReviewsTabsContentLoader(movieId: Int) {
    val viewModel: ReviewsViewModel = hiltViewModel()
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
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(movies) { movie ->
                Box(modifier = Modifier,
                    contentAlignment = Alignment.Center

                ) {
                    AsyncImage(
                        model = IMAGE_URL_COMPOSE + movie.posterPath,
                        contentDescription = "movie image",
                        modifier = Modifier
                            .height(145.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }
        }
    )
}

@Composable
fun LazyGridReviews(reviews: List<Review>){

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth(0.84f),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(reviews) { review ->
                Row(modifier= Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(R.drawable.profilepic), contentDescription = "profilepic",
                            modifier = Modifier.size(44.dp))
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(text = review.authorDetails?.rating?.toString() ?: "X", color = Color(0xff0296E5))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(

                    ) {
                        Text(
                            text = review.author ?: "Author",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = review.content ?: "No content",
                            color = Color.White,
                            fontSize = 13.sp,
                            maxLines = 4
                        )
                    }
                }

            }
        }
    )
}


