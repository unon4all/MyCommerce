package com.example.mycommerce.data.localDatabase.repository

import com.example.mycommerce.data.localDatabase.dao.OrderHistoryDAO
import com.example.mycommerce.data.localDatabase.models.OrderHistoryItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class OrderHistoryRepository @Inject constructor(private val orderHistoryDao: OrderHistoryDAO) {

    /**
     * Retrieves order history for a specific user.
     * @param userId ID of the user whose order history to retrieve.
     * @return Flow representing the list of order history items for the user.
     */
    suspend fun getOrderHistory(userId: String): Flow<List<OrderHistoryItem>> {
        return orderHistoryDao.getOrderHistory(userId)
    }

    /**
     * Adds a new order history item to the database.
     * @param roomOrderHistoryItem The order history item to add.
     */
    suspend fun addOrderHistoryItem(roomOrderHistoryItem: OrderHistoryItem) {
        orderHistoryDao.insertOrderHistory(roomOrderHistoryItem)
    }
}
