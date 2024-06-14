package com.example.mycommerce.data.localDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uid: String, val username: String, val email: String
)
