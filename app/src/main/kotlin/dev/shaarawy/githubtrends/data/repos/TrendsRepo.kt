package dev.shaarawy.githubtrends.data.repos

import dev.shaarawy.githubtrends.data.cache.daos.TrendRepoDao
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity
import dev.shaarawy.githubtrends.data.remote.services.SearchService
import dev.shaarawy.githubtrends.data.repos.dtos.TrendRepoItem
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TrendsRepo {
    fun trendsRepoItemsFlow(): Flow<List<TrendRepoItem>>
}

class TrendsRepoImpl @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val searchService: SearchService,
    private val trendRepoDao: TrendRepoDao,
) : TrendsRepo {
    override fun trendsRepoItemsFlow(): Flow<List<TrendRepoItem>> = trendRepoDao.getAllRepos().map {
        it.map { entity ->
            TrendRepoItem(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                url = entity.url,
                language = entity.language,
                starsCount = entity.starsCount,
                owner = TrendRepoItem.Owner(
                    id = entity.owner.id,
                    name = entity.owner.name,
                    avatarUrl = entity.owner.avatarUrl,
                    url = entity.owner.url
                )
            )
        }
    }.onStart {
        fetchAndInsertDataToCache()
    }

    private suspend fun fetchAndInsertDataToCache() = withContext(dispatchersProvider.io) {
        searchService.getTendingRepos().items?.map {
            TrendRepoEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url,
                language = it.language,
                starsCount = it.stargazersCount,
                owner = TrendRepoEntity.Owner(
                    id = it.owner?.id,
                    name = it.owner?.login,
                    avatarUrl = it.owner?.avatarUrl,
                    url = it.owner?.url
                )
            )
        }?.also {
            trendRepoDao.bulkInsert(it)
        }
    }
}