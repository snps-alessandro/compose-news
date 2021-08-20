package it.alexs.sharelibs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import it.alexs.sharelibs.network.FakeNewsApiService
import it.alexs.sharelibs.network.NewsApiService
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreModule::class]
)
@Module
class FakeCoreModule {

    @Singleton
    @Provides
    fun provideFakeNewsApiService(): NewsApiService {
        return FakeNewsApiService()
    }
}