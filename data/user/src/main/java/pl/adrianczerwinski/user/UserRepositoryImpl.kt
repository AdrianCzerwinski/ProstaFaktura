package pl.adrianczerwinski.user

import kotlinx.coroutines.flow.Flow
import pl.adrianczerwinski.user.database.UserDataStore
import pl.adrianczerwinski.user.database.UserPreferencesKeys.USER_REGISTERED
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : UserRepository {
    override suspend fun setUserRegistered() {
        userDataStore.putBoolean(USER_REGISTERED, true)
    }

    override fun getUserRegistered(): Flow<Boolean?> = userDataStore.getBoolean(USER_REGISTERED)
}
