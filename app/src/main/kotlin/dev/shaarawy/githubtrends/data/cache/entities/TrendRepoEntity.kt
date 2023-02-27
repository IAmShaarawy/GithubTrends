package dev.shaarawy.githubtrends.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("repos")
data class TrendRepoEntity(
    @ColumnInfo("id") @PrimaryKey val id: Int?,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("desc") val description: String?,
    @ColumnInfo("url") val url: String?,
    @ColumnInfo("language") val language: String?,
    @ColumnInfo("stars_count") val starsCount: Int?,
    @Embedded(prefix = "owner_") val owner: Owner
) {
    @Entity("owner")
    data class Owner(
        @ColumnInfo("id") val id: Int?,
        @ColumnInfo("name") val name: String?,
        @ColumnInfo("avatar_ur") val avatarUrl: String?,
        @ColumnInfo("url") val url: String?
    )
}