package pl.adrianczerwinski.user.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.user.database.model.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE user.id = 1")
    fun getUser(): Flow<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserModel)
}
