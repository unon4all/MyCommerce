package com.example.mycommerce.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycommerce.data.models.UserAddressDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAddress(userAddressDetails: UserAddressDetails)

    @Query("SELECT * FROM user_address_details WHERE userId = :userId")
    fun getUserAddresses(userId: String): Flow<List<UserAddressDetails>>

    @Query("SELECT * FROM user_address_details WHERE id = :id")
    fun getAddressById(id: Int): Flow<UserAddressDetails>

    @Update
    suspend fun updateAddress(userAddressDetails: UserAddressDetails)

    @Delete
    suspend fun deleteAddress(userAddressDetails: UserAddressDetails)

    @Query("UPDATE user_address_details SET isDefault = CASE WHEN id = :id THEN 1 ELSE 0 END WHERE userId = :userId")
    suspend fun updateDefaultAddress(id: Int, userId: String)
}

