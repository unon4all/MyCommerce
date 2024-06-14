package com.example.mycommerce.data.repository

import com.example.mycommerce.data.dao.OrderHistoryDAO
import com.example.mycommerce.data.models.OrderHistoryItem
import com.example.mycommerce.data.models.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@ViewModelScoped
class OrderHistoryRepository @Inject constructor(
    private val orderHistoryDao: OrderHistoryDAO, private val userRepository: UserRepository
) {

    suspend fun addOrderHistoryItem(orderHistoryItem: OrderHistoryItem) {
        orderHistoryDao.insertOrderHistory(orderHistoryItem)
    }

    fun getOrderHistory(userId: String): Flow<List<OrderHistoryItem>> {
        return orderHistoryDao.getOrderHistory(userId)
    }

    suspend fun getAllUsersWithOrders(): List<Pair<User, List<OrderHistoryItem>>> {
        val allUsers = userRepository.getAllUsers.firstOrNull() ?: emptyList()
        val usersWithOrders = mutableListOf<Pair<User, List<OrderHistoryItem>>>()

        for (user in allUsers) {
            val orders = orderHistoryDao.getOrderHistory(user.id).first()
            usersWithOrders.add(Pair(user, orders))
        }

        return usersWithOrders
    }
}

