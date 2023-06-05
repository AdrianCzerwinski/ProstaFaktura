package pl.adrianczerwinski.client.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.client.database.ClientDao
import pl.adrianczerwinski.client.database.ClientDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ClientDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = ClientDatabase::class.java,
            name = "client_db"
        ).build()

    @Provides
    fun provideUserDao(database: ClientDatabase): ClientDao = database.clientDao()
}
