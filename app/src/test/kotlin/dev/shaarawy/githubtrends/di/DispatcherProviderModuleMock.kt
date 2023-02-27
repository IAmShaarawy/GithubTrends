package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import dev.shaarawy.githubtrends.foundation.DispatchersProviderImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherProviderModule::class]
)
class DispatcherProviderModuleMock {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestDispatcherQualifier

    @TestDispatcherQualifier
    @Singleton
    @Provides
    fun provideStandardTestDispatcher() = StandardTestDispatcher()

    @Singleton
    @Provides
    fun provideDispatchersProvider(
        @TestDispatcherQualifier testDispatcher: TestDispatcher
    ): DispatchersProvider = DispatchersProviderImpl(
        io = testDispatcher,
        default = testDispatcher,
    )
}