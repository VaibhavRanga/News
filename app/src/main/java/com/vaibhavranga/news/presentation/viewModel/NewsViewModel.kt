package com.vaibhavranga.news.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavranga.news.AppState
import com.vaibhavranga.news.data.repo.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        getHeadlines(country = "us")
    }

    fun getHeadlines(country: String) {
        _state.update {
            it.copy(
                highlightedSearchCategory = "top headlines"
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getHeadlines(country = country).collectLatest { value ->
                if (value.loading) {
                    _state.update {
                        it.copy(
                            loading = true,
                        )
                    }
                } else if (value.error?.isNotBlank() == true) {
                    _state.update {
                        it.copy(
                            loading = false,
                            error = value.error
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            loading = false,
                            data = value.data
                        )
                    }
                }
            }
        }
    }

    fun getEverything() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchQuery = _state.value.searchQuery.value

            resetSelectedSearchCategory()

            if (searchQuery.isNotBlank()) {
                newsRepository.getEverything(query = _state.value.searchQuery.value)
                    .collectLatest { value ->
                        if (value.loading) {
                            _state.update {
                                it.copy(
                                    loading = true
                                )
                            }
                        } else if (value.error?.isNotBlank() == true) {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    error = value.error
                                )
                            }
                        } else {
                            Log.d("TAG", "getHeadlines: ${value.data}")
                            _state.update {
                                it.copy(
                                    loading = false,
                                    data = value.data
                                )
                            }
                        }
                    }
            } else {
                _state.update {
                    it.copy(
                        error = "Enter search query"
                    )
                }
            }
        }
    }

    private fun resetSelectedSearchCategory() {
        if (state.value.searchQuery.value != state.value.highlightedSearchCategory) {
            _state.update {
                it.copy(
                    highlightedSearchCategory = ""
                )
            }
        }
    }
}
