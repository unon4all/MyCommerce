package com.example.mycommerce.data.localDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycommerce.data.localDatabase.models.OrderHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderHistory(orderHistoryItem: OrderHistoryItem)

    @Query("SELECT * FROM order_history WHERE userId = :userId")
    suspend fun getOrderHistory(userId: String): Flow<List<OrderHistoryItem>>
}
