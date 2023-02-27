package dev.shaarawy.githubtrends.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpClientModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(domain)
        .addConverterFactory(DefaultJsonConverter)
        .build()

    companion object {
        private const val domain = "https://api.github.com/"
        private val jsonMediaType = "application/json".toMediaType()
        private val json = Json { ignoreUnknownKeys = true }

        @OptIn(ExperimentalSerializationApi::class)
        val DefaultJsonConverter = json.asConverterFactory(jsonMediaType)
    }
}