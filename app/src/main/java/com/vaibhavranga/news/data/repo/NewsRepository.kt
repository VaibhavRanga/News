package com.vaibhavranga.news.data.repo

import com.vaibhavranga.news.data.apiService.ApiService
import com.vaibhavranga.news.data.models.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {

    fun getHeadlines(country: String): Flow<ApiState> {
        return flow {
            emit(
                ApiState(
                    loading = true
                )
            )
            try {
                val response = apiService.getHeadlines(country = country)
                emit(
                    ApiState(
                        loading = false,
                        data = response
                    )
                )
            } catch (e: HttpException) {
                emit(
                    ApiState(
                        loading = false,
                        error = e.localizedMessage
                    )
                )
            } catch (e: Exception) {
                emit(
                    ApiState(
                        loading = false,
                        error = e.localizedMessage
                    )
                )
            }
        }
    }

    fun getEverything(query: String): Flow<ApiState> {
        return flow {
            emit(
                ApiState(
                    loading = true
                )
            )
            try {
                val response = apiService.getEverything(query = query)
                emit(
                    ApiState(
                        loading = false,
                        data = response
                    )
                )
            } catch (e: HttpException) {
                emit(
                    ApiState(
                        loading = false,
                        error = e.localizedMessage
                    )
                )
            } catch (e: Exception) {
                emit(
                    ApiState(
                        loading = false,
                        error = e.localizedMessage
                    )
                )
            }
        }
    }
}

data class ApiState(
    val loading: Boolean = false,
    val error: String? = "",
    val data: ApiResponse? = null
)