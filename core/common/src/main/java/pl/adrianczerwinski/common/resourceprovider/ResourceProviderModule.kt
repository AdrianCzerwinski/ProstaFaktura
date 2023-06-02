package pl.adrianczerwinski.common.resourceprovider

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ResourceProviderModule {

    @Binds
    fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}
