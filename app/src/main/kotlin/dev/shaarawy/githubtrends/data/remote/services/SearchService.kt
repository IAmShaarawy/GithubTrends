package dev.shaarawy.githubtrends.data.remote.services

import dev.shaarawy.githubtrends.data.remote.dtos.TrendingReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/repositories")
    suspend fun getTendingRepos(@Query("q") one: String = "language=+sort:stars"): TrendingReposResponse
}