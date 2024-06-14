package com.example.mycommerce.data.localDatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ecommerce_items")
data class ECommerceItem(
    @PrimaryKey val id: String, val itemName: String, val itemPrice: Double
)
