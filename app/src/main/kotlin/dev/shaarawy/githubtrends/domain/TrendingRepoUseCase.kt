package dev.shaarawy.githubtrends.domain

import dev.shaarawy.githubtrends.data.repos.HexColorRepo
import dev.shaarawy.githubtrends.data.repos.TrendsRepo
import dev.shaarawy.githubtrends.data.repos.dtos.TrendRepoItem
import dev.shaarawy.githubtrends.domain.dtos.TrendRepoModel
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TrendingRepoUseCase : () -> Flow<List<TrendRepoModel>>

class TrendingRepoUseCaseImpl @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val prettyCountUseCase: PrettyCountUseCase,
    private val trendsRepo: TrendsRepo,
    private val hexColorRepo: HexColorRepo,
) : TrendingRepoUseCase {
    override fun invoke(): Flow<List<TrendRepoModel>> = trendsRepo.trendsRepoItemsFlow()
        .map { it.mapNotNull { validateAndMapRepoItem(it) } }
        .flowOn(dispatchersProvider.default)

    private suspend fun validateAndMapRepoItem(input: TrendRepoItem): TrendRepoModel? {

        if (input.id == null || input.name == null ||
            input.description == null || input.url == null ||
            input.language == null || input.starsCount == null ||
            input.owner.id == null || input.owner.name == null ||
            input.owner.avatarUrl == null || input.owner.url == null
        ) return null

        return TrendRepoModel(
            id = input.id,
            name = input.name,
            description = input.description,
            url = input.url,
            language = input.language,
            starsCount = prettyCountUseCase.invoke(input.starsCount),
            colorHex = hexColorRepo.provideForLanguage(input.language) ?: "#fff",
            owner = TrendRepoModel.Owner(
                id = input.owner.id,
                name = input.owner.name,
                avatarUrl = input.owner.avatarUrl,
                url = input.owner.url
            )
        )

    }
}