package dev.shaarawy.githubtrends.data.repos

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.shaarawy.githubtrends.foundation.json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

interface HexColorRepo {
    suspend fun provideForLanguage(language: String): String?
}

class HexColorRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : HexColorRepo {
    override suspend fun provideForLanguage(language: String): String? {
        val jsonString =
            context.assets.open("GithubLanguages.json").bufferedReader().use { it.readText() }
        return json.decodeFromString<JsonElement>(jsonString)
            .jsonObject[language]
            ?.jsonObject?.get("color")?.jsonPrimitive?.content
    }
}