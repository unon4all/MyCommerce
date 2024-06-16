package com.example.mycommerce.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mycommerce.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "my_commerce_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    fun provideOrderHistoryDao(db: AppDatabase) = db.orderHistoryDao()

    @Provides
    fun provideECommerceItemDao(db: AppDatabase) = db.eCommerceItemDao()

    @Provides
    fun provideProductDao(db: AppDatabase) = db.userAddressDetailsDao()

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("my_app_pref", Context.MODE_PRIVATE)
    }
}