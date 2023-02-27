package dev.shaarawy.githubtrends.data.repos

import dev.shaarawy.githubtrends.data.repos.dtos.TrendRepoItem
import kotlinx.coroutines.flow.Flow

interface TrendsRepo {
    fun trendsRepoItemsFlow(): Flow<List<TrendRepoItem>>
}