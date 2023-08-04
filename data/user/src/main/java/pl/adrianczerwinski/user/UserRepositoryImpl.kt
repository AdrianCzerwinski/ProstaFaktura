package pl.adrianczerwinski.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.adrianczerwinski.user.database.UserDao
import pl.adrianczerwinski.user.database.UserDataStore
import pl.adrianczerwinski.user.database.UserPreferencesKeys.USER_REGISTERED
import pl.adrianczerwinski.user.database.toModel
import pl.adrianczerwinski.user.database.toUser
import pl.adrianczerwinski.user.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun setOnboardingShown() {
        userDataStore.putBoolean(USER_REGISTERED, true)
    }

    override fun getOnboardingShown(): Flow<Boolean?> = userDataStore.getBoolean(USER_REGISTERED)
    override fun getUser(): Flow<User?> = userDao.getUser().map { it.toUser() }

    override suspend fun saveUser(user: User) = userDao.saveUser(user.toModel())
}
