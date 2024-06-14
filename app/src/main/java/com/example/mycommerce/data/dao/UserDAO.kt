package com.example.mycommerce.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycommerce.data.localDatabase.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE uid = :userId")
    suspend fun getUser(userId: String): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
