package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.data.services.SearchService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    fun provideArticleService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}