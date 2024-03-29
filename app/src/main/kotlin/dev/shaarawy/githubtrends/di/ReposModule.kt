package dev.shaarawy.githubtrends.di

import dagger.Binds
import dagger.Module

import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.data.repos.HexColorRepo
import dev.shaarawy.githubtrends.data.repos.HexColorRepoImpl
import dev.shaarawy.githubtrends.data.repos.TrendsRepo
import dev.shaarawy.githubtrends.data.repos.TrendsRepoImpl


@Module
@InstallIn(SingletonComponent::class)
interface ReposModule {

    @Binds
    fun provideTrendsRepo(trendsRepoImpl: TrendsRepoImpl): TrendsRepo

    @Binds
    fun provideHexColorRepo(trendsRepoImpl: HexColorRepoImpl): HexColorRepo
}