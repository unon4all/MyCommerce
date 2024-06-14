package com.example.mycommerce.data.localDatabase.repository

import android.util.Log
import com.example.mycommerce.data.localDatabase.dao.UserDAO
import com.example.mycommerce.data.localDatabase.models.User
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
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
    private val userDao: UserDAO, private val db: FirebaseFirestore
) {
    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User) {
        // Insert into local database
        userDao.insertUser(user)

        // Insert into Firestore
        val userMap = hashMapOf(
            "uid" to user.uid, "username" to user.username, "email" to user.email
        )
        db.collection("users").document(user.uid).set(userMap).addOnSuccessListener {
            Log.d("UserRepository", "User added to Firestore successfully")
        }.addOnFailureListener { e ->
            Log.e("UserRepository", "Error adding user to Firestore", e)
        }
    }

    fun getUser(userId: String): Flow<User?> {
        return userDao.getUser(userId)
    }
}





