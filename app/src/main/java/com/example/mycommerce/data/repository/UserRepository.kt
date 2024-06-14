package com.example.mycommerce.data.repository

import android.util.Log
import com.example.mycommerce.data.dao.UserDAO
import com.example.mycommerce.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject


//@ViewModelScoped
//class UserRepository @Inject constructor(private val userDao: UserDAO) {
//
//    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()
//
//    suspend fun insertUser(user: User) {
//        userDao.insertUser(user)
//    }
//
//    fun getUser(userId: String): Flow<User?> {
//        return userDao.getUser(userId)
//    }
//}

@ViewModelScoped
class UserRepository @Inject constructor(
    private val userDao: UserDAO,
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
}