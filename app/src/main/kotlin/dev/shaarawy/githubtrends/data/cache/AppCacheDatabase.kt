package dev.shaarawy.githubtrends.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.shaarawy.githubtrends.data.cache.daos.TrendRepoDao
import dev.shaarawy.githubtrends.data.cache.entities.TrendRepoEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [TrendRepoEntity::class]
)
abstract class AppCacheDatabase : RoomDatabase() {
    abstract fun trendReposDao(): TrendRepoDao
}