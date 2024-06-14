package com.example.mycommerce.data.repository

import com.example.mycommerce.data.dao.OrderHistoryDAO
import com.example.mycommerce.data.models.OrderHistoryItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class OrderHistoryRepository @Inject constructor(private val orderHistoryDao: OrderHistoryDAO) {

    suspend fun addOrderHistoryItem(orderHistoryItem: OrderHistoryItem) {
        orderHistoryDao.insertOrderHistory(orderHistoryItem)
    }

    fun getOrderHistory(userId: String): Flow<List<OrderHistoryItem>> {
        return orderHistoryDao.getOrderHistory(userId)
    }
}

