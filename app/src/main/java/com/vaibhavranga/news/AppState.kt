package com.vaibhavranga.news

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.vaibhavranga.news.data.models.ApiResponse

data class AppState(
    val loading: Boolean = false,
    val error: String = "",
    val data: ApiResponse? = null,
    val searchQuery: MutableState<String> = mutableStateOf(""),
    val searchCategories: MutableList<String> = mutableListOf("top headlines", "nature", "technology", "sports", "finance", "world", "space", "science", "health", "business", "entertainment", "social").toMutableStateList(),
    var highlightedSearchCategory: String = "top headlines"
)
