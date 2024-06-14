package com.example.mycommerce.data.localDatabase.di

import android.content.Context
import androidx.room.Room
import com.example.mycommerce.data.localDatabase.AppDatabase
import com.example.mycommerce.data.localDatabase.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return Room.databaseBuilder(
//            appContext, AppDatabase::class.java, "my_commerce_db"
//        ).build()
//    }
//
//    @Provides
//    fun provideUserDao(db: AppDatabase): UserDAO {
//        return db.userDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(userDao: UserDAO): UserRepository {
//        return UserRepository(userDao)
//    }
//
//    @Provides
//    fun provideOrderHistoryDao(db: AppDatabase): OrderHistoryDAO {
//        return db.orderHistoryDao()
//    }
//
//    @Provides
//    fun provideECommerceItemDao(db: AppDatabase): ECommerceItemDAO {
//        return db.eCommerceItemDao()
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "my_commerce_db"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
}

