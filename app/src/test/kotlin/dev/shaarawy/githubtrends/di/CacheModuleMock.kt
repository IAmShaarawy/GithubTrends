package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.shaarawy.githubtrends.data.cache.daos.TrendRepoDao
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CacheModule::class]
)
class CacheModuleMock {

    @Provides
    fun provideTrendRepoDao(): TrendRepoDao = object : TrendRepoDao {
        private val cache: MutableStateFlow<List<TrendRepoEntity>> = MutableStateFlow(emptyList())
        override suspend fun bulkInsert(repos: List<TrendRepoEntity>) {
            cache.emit(repos)
        }

        override fun getAllRepos(): Flow<List<TrendRepoEntity>> = cache
    }
}