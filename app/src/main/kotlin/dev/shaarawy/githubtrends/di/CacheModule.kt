package dev.shaarawy.githubtrends.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.githubtrends.data.cache.AppCacheDatabase
import dev.shaarawy.githubtrends.data.cache.daos.TrendRepoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Singleton
    @Provides
    fun provideCacheDatabase(@ApplicationContext context: Context): AppCacheDatabase =
        Room.databaseBuilder(context, AppCacheDatabase::class.java, "app_cache")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideTrendRepoDao(appCacheDatabase: AppCacheDatabase): TrendRepoDao =
        appCacheDatabase.trendReposDao()
}