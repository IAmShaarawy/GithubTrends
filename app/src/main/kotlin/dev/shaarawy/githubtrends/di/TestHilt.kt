package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

data class TestHilt(val data: String)

@Module
@InstallIn(SingletonComponent::class)
class TestHiltModule {
    @Provides
    fun testHilt() = TestHilt("i am a result")
}