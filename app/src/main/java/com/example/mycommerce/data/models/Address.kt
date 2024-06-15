package com.example.mycommerce.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String?,
    val fullName: String = "",
    val phoneNumber: String = "",
    val alternateNumber: String = "",
    val pinCode: String = "",
    val state: String = "",
    val city: String = "",
    val address: String = "",
    val areaName: String = "",
    val landmark: String = "",
    val country: String = "",
    val typeOfAddress: Int = 0,
    val onError: String = ""
)
