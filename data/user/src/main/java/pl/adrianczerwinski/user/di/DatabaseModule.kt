package pl.adrianczerwinski.user.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.user.database.UserDao
import pl.adrianczerwinski.user.database.UserDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): UserDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = UserDatabase::class.java,
            name = "user_db"
        ).build()

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}
