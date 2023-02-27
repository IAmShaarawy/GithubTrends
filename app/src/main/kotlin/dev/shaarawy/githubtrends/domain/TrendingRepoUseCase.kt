package dev.shaarawy.githubtrends.domain

import dev.shaarawy.githubtrends.data.repos.TrendsRepo
import dev.shaarawy.githubtrends.domain.dtos.TrendRepoModel
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TrendingRepoUseCase : () -> Flow<List<TrendRepoModel>>

class TrendingRepoUseCaseImpl @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val trendsRepo: TrendsRepo
) : TrendingRepoUseCase {
    override fun invoke(): Flow<List<TrendRepoModel>> = TODO("missing impl")
}