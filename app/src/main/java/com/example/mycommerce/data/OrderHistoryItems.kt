package com.example.mycommerce.data

data class OrderHistoryItem(
    val items: List<ECommerceItem>,
    val status: OrderStatus,
    val totalPrice: Int
)

enum class OrderStatus {
    PLACED,
    FAILED,
    IN_PROGRESS,
    INITIATED
}