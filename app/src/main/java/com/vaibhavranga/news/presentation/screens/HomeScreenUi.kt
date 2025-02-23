package com.vaibhavranga.news.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vaibhavranga.news.AppState
import com.vaibhavranga.news.R
import com.vaibhavranga.news.data.models.Article

@Composable
fun HomeScreenUi(
    state: AppState,
    onSearchIconClicked: () -> Unit,
    onNewsArticleClicked: (article: Article) -> Unit,
    onTopHeadlinesButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val articles = state.data?.articles?.filter { article ->
        article.title != "[Removed]"
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)
    ) {
        SearchTextField(
            state = state,
            onSearchIconClicked = onSearchIconClicked
        )
        SearchCategoryRow(
            state = state,
            onSearchIconClicked = onSearchIconClicked,
            onTopHeadlinesButtonClicked = onTopHeadlinesButtonClicked
        )

        if (state.loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error.isNotBlank()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = state.error)
            }
        } else if (articles.isNullOrEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "No article found")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = articles) { article ->
                    NewsListItem(
                        article = article,
                        onNewsArticleClicked = onNewsArticleClicked
                    )
                }
            }
        }
    }
}

@Composable
fun NewsListItem(
    article: Article,
    onNewsArticleClicked: (article: Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.5F),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable {
                onNewsArticleClicked(article)
            }
    ) {
        if (article.urlToImage != null) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.no_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Text(text = article.title!!)
    }
}

@Composable
fun SearchTextField(
    state: AppState,
    onSearchIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = state.searchQuery.value,
        onValueChange = {
            state.searchQuery.value = it
        },
        label = {
            Text(text = "Search")
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onSearchIconClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Click to search"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchIconClicked()
            }
        ),
        maxLines = 3,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun SearchCategoryRow(
    state: AppState,
    onSearchIconClicked: () -> Unit,
    onTopHeadlinesButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        state = scrollState
    ) {
        items(items = state.searchCategories) { searchCategory ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (state.highlightedSearchCategory == searchCategory)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        if (searchCategory != "top headlines") {
                            state.searchQuery.value = searchCategory
                            state.highlightedSearchCategory = searchCategory
                            onSearchIconClicked()
                        } else {
                            onTopHeadlinesButtonClicked()
                        }
                    }
            ) {
                Text(text = searchCategory, Modifier.padding(8.dp))
            }
        }
    }
}
