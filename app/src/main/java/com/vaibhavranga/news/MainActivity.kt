package com.vaibhavranga.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vaibhavranga.news.data.models.Article
import com.vaibhavranga.news.data.models.Source
import com.vaibhavranga.news.presentation.navigation.DetailedArticleScreen
import com.vaibhavranga.news.presentation.navigation.HomeScreen
import com.vaibhavranga.news.presentation.screens.DetailedArticleScreenUI
import com.vaibhavranga.news.presentation.screens.HomeScreenUi
import com.vaibhavranga.news.presentation.viewModel.NewsViewModel
import com.vaibhavranga.news.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                Scaffold(
                    topBar = {
                        MyTopAppBar(modifier = Modifier.fillMaxWidth())
                    },
                    modifier = Modifier
                ) { innerPadding ->

                    val newsViewModel by viewModels<NewsViewModel>()
                    val state by newsViewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = HomeScreen) {

                        composable<HomeScreen> {
                            HomeScreenUi(
                                state = state,
                                onSearchIconClicked = newsViewModel::getEverything,
                                onNewsArticleClicked = {
                                    navController.navigate(
                                        DetailedArticleScreen(
                                            author = it.author,
                                            content = it.content,
                                            description = it.description,
                                            publishedAt = it.publishedAt,
                                            id = it.source?.id,
                                            name = it.source?.name,
                                            title = it.title,
                                            url = it.url,
                                            urlToImage = it.urlToImage
                                        )
                                    )
                                },
                                onTopHeadlinesButtonClicked = {
                                    newsViewModel.getHeadlines(country = "us")
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable<DetailedArticleScreen> {

                            val incomingData = it.toRoute<DetailedArticleScreen>()
                            val article = Article(
                                author = incomingData.author,
                                content = incomingData.content,
                                description = incomingData.description,
                                publishedAt = incomingData.publishedAt,
                                source = Source(
                                    id = incomingData.id,
                                    name = incomingData.name
                                ),
                                title = incomingData.title,
                                url = incomingData.url,
                                urlToImage = incomingData.urlToImage
                            )
                            DetailedArticleScreenUI(
                                article = article,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "News")
        },
        modifier = modifier
    )
}