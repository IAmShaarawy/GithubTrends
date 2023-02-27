package dev.shaarawy.githubtrends.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.domain.TrendingRepoUseCase
import dev.shaarawy.githubtrends.domain.TrendingRepoUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesModule {
    @Binds
    fun provideTrendsRepo(trendingRepoUseCaseImpl: TrendingRepoUseCaseImpl): TrendingRepoUseCase
}