package dev.shaarawy.githubtrends.data.repos

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface HexColorRepo {
    suspend fun provideForLanguage(language: String): String?
}

class HexColorRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : HexColorRepo {
    override suspend fun provideForLanguage(language: String): String? {
        TODO("not implemented")
    }
}