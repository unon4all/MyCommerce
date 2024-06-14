package com.example.mycommerce.data.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycommerce.data.localDatabase.dao.ECommerceItemDAO
import com.example.mycommerce.data.localDatabase.dao.OrderHistoryDAO
import com.example.mycommerce.data.localDatabase.dao.UserDAO
import com.example.mycommerce.data.localDatabase.models.ECommerceItem
import com.example.mycommerce.data.localDatabase.models.OrderHistoryItem
import com.example.mycommerce.data.localDatabase.models.User

//@Database(entities = [User::class, OrderHistoryItem::class, ECommerceItem::class], version = 1)
//@TypeConverters(Converters::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDAO
//    abstract fun orderHistoryDao(): OrderHistoryDAO
//    abstract fun eCommerceItemDao(): ECommerceItemDAO
//}

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class) // Add if you have converters
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
}
