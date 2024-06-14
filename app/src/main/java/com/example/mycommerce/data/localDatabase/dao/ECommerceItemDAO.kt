package com.example.mycommerce.data.localDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycommerce.data.localDatabase.models.ECommerceItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ECommerceItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ECommerceItem)

    @Query("SELECT * FROM ecommerce_items WHERE id = :itemId")
    suspend fun getItem(itemId: String): Flow<ECommerceItem?>
}
