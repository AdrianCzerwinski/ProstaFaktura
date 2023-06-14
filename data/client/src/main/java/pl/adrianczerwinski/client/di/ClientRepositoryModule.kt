package pl.adrianczerwinski.client.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.client.ClientRepository
import pl.adrianczerwinski.client.ClientRepositoryImpl

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class ClientRepositoryModule {

    @Binds
    abstract fun clientRepository(impl: ClientRepositoryImpl): ClientRepository
}
