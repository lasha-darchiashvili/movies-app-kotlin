package com.example.moviesapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.Movie
import com.example.moviesapp.R
import com.example.moviesapp.SearchedMovie
import com.example.moviesapp.SearchedMovies
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.SearchViewModel
import com.example.moviesapp.viewmodels.SearchViewModelFactory
import com.example.moviesapp.viewmodels.SimilarMoviesViewModel

@Composable
fun SearchMovieComp(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val viewModel: SearchViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsState()
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF242A32)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header({ navController.popBackStack() }, title = "Search", rightSideIcon = R.drawable.info_icon)
        Spacer(modifier=Modifier.height(30.dp))

        Box(modifier = Modifier.fillMaxWidth(0.88F),
            contentAlignment = Alignment.Center){

            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    println("quer $query")
                    viewModel.loadMovies(it)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF3A3F47))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                singleLine = true,

                )
        }

        if (query.isNotEmpty()) {
            SearchedMoviesLoader(viewState)
        }

    }

}


@Composable
fun SearchedMoviesLoader(viewState: SearchViewModel.ViewState) {
    when (viewState) {
        is SearchViewModel.ViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(top=30.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(
                    color = Color.Gray
                )
            }
        }

        is SearchViewModel.ViewState.Error -> {
            println("error")
        }

        is SearchViewModel.ViewState.Success -> {
            val searchedMovies = (viewState as SearchViewModel.ViewState.Success).data?.results ?: emptyList()
            println(searchedMovies)
            LazyGridMovies(searchedMovies)

        }
    }
}


@Composable
fun LazyGridMovies(movies: List<SearchedMovie>){

    if(movies.isEmpty()) {
    Box(modifier = Modifier.padding(top=80.dp), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(text="we are sorry, we can not find the movie :(", fontSize = 16.sp, color=Color(0xffffffff), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text="Find your movie by Type title, categories, years, etc", fontSize = 12.sp, color=Color(0xff92929D), textAlign = TextAlign.Center)

        }
    }
    }

    else {


        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxWidth(0.88f),

            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(movies) { movie ->
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {

                        Box(
                            modifier = Modifier,
                            contentAlignment = Alignment.Center

                        ) {
                            AsyncImage(
                                model = IMAGE_URL_COMPOSE + movie.posterPath,
                                contentDescription = "movie image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column() {
                            Text(
                                text = movie.title ?: "title", fontSize = 16.sp,
                                color = Color(0xffffffff)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Row() {
                                Image(
                                    painter = painterResource(R.drawable.starr),
                                    contentDescription = "starIcon",
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = movie.voteAverage?.toString() ?: "vote",
                                    fontSize = 12.sp,
                                    color = Color(0xffFF8700)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row() {
                                Image(
                                    painter = painterResource(R.drawable.calendar),
                                    contentDescription = "starIcon",
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    fontSize = 12.sp, fontWeight = FontWeight.W500,
                                    color = Color(0xffffffff),
                                    text = if (!movie.releaseDate.isNullOrEmpty() && movie.releaseDate.length >= 4) {
                                        movie.releaseDate.substring(0, 4)
                                    } else {
                                        "unknown"
                                    }
                                )
                            }

                        }
                    }

                }
            }
        )
    }
}