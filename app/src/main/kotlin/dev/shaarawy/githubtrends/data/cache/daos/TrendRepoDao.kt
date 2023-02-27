package dev.shaarawy.githubtrends.data.cache.daos

import androidx.room.Dao
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendRepoDao {

    suspend fun bulkInsert(repos: List<TrendRepoEntity>)

    fun getAllRepos(): Flow<List<TrendRepoEntity>>
}