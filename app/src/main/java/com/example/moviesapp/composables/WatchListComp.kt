package com.example.moviesapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.app.App
import com.example.moviesapp.repository.MoviesRepositoryImpl
import com.example.moviesapp.viewmodels.MoviesDatabaseViewModel
import com.example.moviesapp.viewmodels.MoviesDatabaseViewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.JdkConstants

@Composable
fun WatchListComp(navController: NavController, innerPadding: PaddingValues) {
    val context = LocalContext.current
    val viewModel: MoviesDatabaseViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    val movieList = viewState.movies


    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF242A32)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header({ navController.popBackStack() }, title = "Watch list")

        if(movieList.isEmpty()) {

            Column(modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(modifier=Modifier.fillMaxWidth(0.5f).padding(bottom = innerPadding.calculateBottomPadding())) {
                    Column() {
                        Text(text="There is no added movie yet!", fontSize = 16.sp, color=Color(0xffffffff), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text="Find your movie by Type title, categories, years, etc", fontSize = 12.sp, color=Color(0xff92929D), textAlign = TextAlign.Center)

                    }
                   }

            }

        }

        else {


            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(0.88f),
                verticalArrangement = Arrangement.spacedBy(24.dp),

                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 52.dp,
                    end = 12.dp,
                    bottom = innerPadding.calculateBottomPadding() + 24.dp
                ),
                content = {
                    items(movieList) { movie ->
                        Row() {
                            AsyncImage(
                                model = IMAGE_URL_COMPOSE + movie.poster,
                                contentDescription = "movie image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column() {
                                Text(
                                    text = movie.title,
                                    fontSize = 16.sp,
                                    color = Color(0xffFFFFFF)
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Row() {
                                    Image(
                                        modifier = Modifier.size(13.dp),
                                        painter = painterResource(R.drawable.starr),
                                        contentDescription = "icon"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = String.format("%.1f", movie.voteAverage),
                                        fontSize = 12.sp,
                                        color = Color(0xffeeeeee)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row() {
                                    Image(
                                        modifier = Modifier.size(13.dp),
                                        painter = painterResource(R.drawable.calendar),
                                        contentDescription = "icon"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        fontSize = 12.sp, color = Color(0xffeeeeee),
                                        text = if (movie.releaseDate.length >= 4) {
                                            movie.releaseDate.substring(0, 4)
                                        } else {
                                            "unknown"
                                        }
                                    )
                                }

                            }
                        }
                    }
                })
        }
    }

}
