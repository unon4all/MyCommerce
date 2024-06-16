package com.example.mycommerce.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycommerce.data.dao.AddressDAO
import com.example.mycommerce.data.dao.ECommerceItemDAO
import com.example.mycommerce.data.dao.OrderHistoryDAO
import com.example.mycommerce.data.dao.UserDAO
import com.example.mycommerce.data.extra.Converters
import com.example.mycommerce.data.models.ECommerceItem
import com.example.mycommerce.data.models.OrderHistoryItem
import com.example.mycommerce.data.models.User
import com.example.mycommerce.data.models.UserAddressDetails

@Database(
    entities = [User::class, OrderHistoryItem::class, ECommerceItem::class, UserAddressDetails::class],
    version = 12
)
@TypeConverters(Converters::class) // Add if you have converters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun orderHistoryDao(): OrderHistoryDAO
    abstract fun eCommerceItemDao(): ECommerceItemDAO
    abstract fun userAddressDetailsDao(): AddressDAO
}
