package pl.adrianczerwinski.user.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.adrianczerwinski.user.database.model.UserModel

@Database(entities = [UserModel::class], version = 1)
@TypeConverters(UserConverters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
