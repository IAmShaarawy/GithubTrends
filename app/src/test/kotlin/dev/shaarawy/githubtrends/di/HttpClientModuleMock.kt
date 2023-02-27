package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.shaarawy.githubtrends.di.HttpClientModule.Companion.DefaultJsonConverter
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HttpClientModule::class]
)
class HttpClientModuleMock {
    @Singleton
    @Provides
    fun mockWebServer() = MockWebServer()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(server: MockWebServer): Retrofit = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(DefaultJsonConverter)
        .build()
}