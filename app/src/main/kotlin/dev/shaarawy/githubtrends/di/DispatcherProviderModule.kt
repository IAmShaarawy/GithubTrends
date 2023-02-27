package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import dev.shaarawy.githubtrends.foundation.DispatchersProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherProviderModule {
    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl()
}