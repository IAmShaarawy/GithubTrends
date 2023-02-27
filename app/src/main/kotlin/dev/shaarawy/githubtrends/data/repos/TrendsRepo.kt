package dev.shaarawy.githubtrends.data.repos

import dev.shaarawy.githubtrends.data.remote.services.SearchService
import dev.shaarawy.githubtrends.data.repos.dtos.TrendRepoItem
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface TrendsRepo {
    fun trendsRepoItemsFlow(): Flow<List<TrendRepoItem>>
}

class TrendsRepoImpl @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val searchService: SearchService
) : TrendsRepo {
    override fun trendsRepoItemsFlow(): Flow<List<TrendRepoItem>> = flow {
        searchService.getTendingRepos().items?.map {
            TrendRepoItem(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url,
                language = it.language,
                starsCount = it.stargazersCount,
                owner = TrendRepoItem.Owner(
                    id = it.owner?.id,
                    name = it.owner?.login,
                    avatarUrl = it.owner?.avatarUrl,
                    url = it.owner?.url
                )
            )
        }.let { it ?: emptyList() }
            .also { emit(it) }
    }.flowOn(dispatchersProvider.io)

}