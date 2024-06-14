package com.example.mycommerce.data.localDatabase.repository

import com.example.mycommerce.data.localDatabase.dao.UserDAO
import com.example.mycommerce.data.localDatabase.models.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


@ViewModelScoped
class UserRepository @Inject constructor(private val userDao: UserDAO) {

    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUser(userId: String): Flow<User?> {
        return userDao.getUser(userId)
    }
}




