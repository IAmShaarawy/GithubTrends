package dev.shaarawy.githubtrends.data.services

import dev.shaarawy.githubtrends.data.dtos.TrendingReposResponse

interface SearchService {

    suspend fun getTendingRepos():TrendingReposResponse
}