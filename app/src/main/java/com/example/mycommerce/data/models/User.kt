package com.example.mycommerce.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val profileImage: String,
    val passwordHash: String // Add this field to store hashed passwords
)
