package pl.adrianczerwinski.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.user.UserRepository
import pl.adrianczerwinski.user.UserRepositoryImpl

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserRepositoryModule {

    @Binds
    abstract fun userRepository(impl: UserRepositoryImpl): UserRepository
}
