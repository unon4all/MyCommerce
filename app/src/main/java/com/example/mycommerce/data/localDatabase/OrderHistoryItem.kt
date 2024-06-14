package com.example.mycommerce.data.localDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycommerce.data.frDatabase.OrderStatus

@Entity(tableName = "order_history")
data class OrderHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val items: List<ECommerceItem>,
    val status: OrderStatus,
    val totalPrice: Int
)
