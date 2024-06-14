package com.example.mycommerce.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycommerce.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long // Return Long to check if insertion was successful

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): Flow<User?>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<User?> // Add this method
}

