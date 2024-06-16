package com.example.mycommerce.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val addressId: Int = 0,
    val pinCode: String = "",
    val state: String = "",
    val city: String = "",
    val addressLine: String = "",
    val areaName: String = "",
    val landmark: String = "",
    val typeOfAddress: Int = 0, // 0: Home, 1: Office, 2: Other
)

@Entity(tableName = "user_address_details")
data class UserAddressDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val fullName: String = "",
    val phoneNumber: String = "",
    val alternateNumber: String = "",
    var isDefault: Boolean = false,
    @Embedded(prefix = "address_") val address: Address = Address()
)