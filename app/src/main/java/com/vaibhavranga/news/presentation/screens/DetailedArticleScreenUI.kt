package com.vaibhavranga.news.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vaibhavranga.news.data.models.Article
import com.vaibhavranga.news.data.models.Source
import com.vaibhavranga.news.ui.theme.NewsTheme

@Composable
fun DetailedArticleScreenUI(article: Article, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = article.title.toString(), style = MaterialTheme.typography.titleLarge)
        if (!article.urlToImage.isNullOrBlank()) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
        Text(text = article.description.toString())
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    NewsTheme {
        DetailedArticleScreenUI(article = Article(
            source = Source(
                id = null,
                name = "Yahoo Entertainment"
            ),
            author = "Charlene Chen",
            title = "Billionaires Are Buying a Supercharged Index Fund That Includes Nvidia, Tesla, and Other \"Magnificent Seven\" Stocks",
            description = "The ongoing legal battle between Tesla CEO Elon Musk and OpenAI CEO Sam Altman has revealed previously unknown details of their entrepreneurial journey. Following the US presidential election, Altman has softened his stance, publicly thanking Musk for his con…",
            url = "https://consent.yahoo.com/v2/collectConsent?sessionId=1_cc-session_dd9b5ab9-670a-4f60-8956-3c0f6660f334",
            urlToImage = "https://img.digitimes.com/newsshow/20241213pd209_files/2_b.jpg",
            publishedAt = "2024-12-13T08:30:00Z",
            content = "If you click 'Accept all', we and our partners, including 237 who are part of the IAB Transparency &amp; Consent Framework, will also store and/or access information on a device (in other words, use … [+678 chars]"
        )
        )
    }
}