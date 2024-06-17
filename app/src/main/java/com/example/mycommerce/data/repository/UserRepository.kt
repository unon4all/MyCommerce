package com.example.mycommerce.data.repository

import android.content.SharedPreferences
import com.example.mycommerce.data.dao.UserDAO
import com.example.mycommerce.data.models.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject


@ViewModelScoped
class UserRepository @Inject constructor(
    private val userDao: UserDAO,
    private val sharedPreferences: SharedPreferences // Example dependency for storing user session
) {
    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User, password: String): Boolean {
        // Hash the password before saving
        val passwordHash = hashPassword(password)
        val userWithHashedPassword = user.copy(passwordHash = passwordHash)

        // Insert into local database
        val result = userDao.insertUser(userWithHashedPassword)
        return result != -1L // Return true if insertion was successful
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(userId: String) {
        userDao.deleteUser(userId)
    }

    fun getUser(userId: String): Flow<User?> {
        return userDao.getUser(userId)
    }

    fun getUserByEmail(email: String): Flow<User?> {
        return userDao.getUserByEmail(email)
    }

    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun validatePassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }

    fun getCurrentUser(): Flow<User?> {
        val userId = sharedPreferences.getString("userId", null)
        return userId?.let { userDao.getUser(it) } ?: flowOf(null)
    }

    fun setCurrentUser(userId: String?) {
        with(sharedPreferences.edit()) {
            if (userId == null) {
                remove("userId")
            } else {
                putString("userId", userId)
            }
            apply()
        }
    }
}
