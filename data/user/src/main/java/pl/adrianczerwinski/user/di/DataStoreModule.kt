package pl.adrianczerwinski.user.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.user.database.UserDataStore
import pl.adrianczerwinski.user.database.UserDataStoreImpl
import javax.inject.Singleton

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal interface DataStoreModule {
    companion object {
        @Provides
        @Singleton
        fun providesUserDataStore(
            @ApplicationContext context: Context
        ): UserDataStore = UserDataStoreImpl(context)
    }
}
