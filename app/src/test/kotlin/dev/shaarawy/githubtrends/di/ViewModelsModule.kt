package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.domain.TrendingRepoUseCase
import dev.shaarawy.githubtrends.presentation.main.MainViewModel

@Module
@InstallIn(SingletonComponent::class)
class ViewModelsModule {
    @Provides
    fun provideArticleService(useCase: TrendingRepoUseCase): MainViewModel =
        MainViewModel(useCase)
}