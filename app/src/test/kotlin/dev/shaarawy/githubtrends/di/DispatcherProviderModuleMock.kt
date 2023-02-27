package dev.shaarawy.githubtrends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.shaarawy.githubtrends.foundation.DispatchersProvider
import dev.shaarawy.githubtrends.foundation.DispatchersProviderImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherProviderModule::class]
)
class DispatcherProviderModuleMock {
    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl(
        io = StandardTestDispatcher(),
        default = StandardTestDispatcher(),
    )
}