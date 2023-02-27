package dev.shaarawy.githubtrends.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(repos: List<TrendRepoEntity>)

    @Query("SELECT * FROM repos")
    fun getAllRepos():Flow<List<TrendRepoEntity>>
}