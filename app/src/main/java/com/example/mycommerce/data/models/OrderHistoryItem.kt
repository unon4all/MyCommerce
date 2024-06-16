package com.example.mycommerce.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mycommerce.data.extra.Converters

@Entity(tableName = "order_history")
data class OrderHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    @TypeConverters(Converters::class) val items: List<ECommerceItem>,
    @TypeConverters(Converters::class) val userAddressDetails: UserAddressDetails?,
    val status: OrderStatus,
    val totalPrice: Int,
)
