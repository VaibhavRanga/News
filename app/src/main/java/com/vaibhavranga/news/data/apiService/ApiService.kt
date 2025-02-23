package com.vaibhavranga.news.data.apiService

import com.vaibhavranga.news.data.models.ApiResponse
import com.vaibhavranga.news.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): ApiResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): ApiResponse
}