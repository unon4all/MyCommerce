package com.example.mycommerce.data.repository

import android.content.SharedPreferences
import com.example.mycommerce.data.dao.AddressDAO
import com.example.mycommerce.data.models.Address
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class AddressRepository @Inject constructor(
    private val addressDao: AddressDAO,
    private val sharedPreferences: SharedPreferences // Example dependency for storing user session
) {
    fun getAddressesByUserId(userId: String): Flow<List<Address>> {
        return addressDao.getAddressesByUserId(userId)
    }

    suspend fun insertAddress(address: Address): Boolean {
        // Insert into local database
        val result = addressDao.insertAddress(address)
        return result != -1L // Return true if insertion was successful
    }

    suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address)
    }

    fun getCurrentUserId(): Int {
        // Get the current user ID from shared preferences
        return sharedPreferences.getInt("userId", -1)
    }
}
