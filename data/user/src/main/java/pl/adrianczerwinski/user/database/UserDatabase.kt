package pl.adrianczerwinski.user.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.adrianczerwinski.user.database.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
