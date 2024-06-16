package com.example.mycommerce.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycommerce.data.models.OrderHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderHistory(orderHistoryItem: OrderHistoryItem)

    @Query("SELECT * FROM order_history WHERE userId = :userId")
     fun getOrderHistory(userId: String): Flow<List<OrderHistoryItem>>
}