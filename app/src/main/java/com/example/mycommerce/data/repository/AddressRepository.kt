package com.example.mycommerce.data.repository

import com.example.mycommerce.data.dao.AddressDAO
import com.example.mycommerce.data.models.UserAddressDetails
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@ViewModelScoped
class AddressRepository @Inject constructor(private val addressDAO: AddressDAO) {
    suspend fun insertAddress(userAddressDetails: UserAddressDetails) {
        addressDAO.insertAddress(userAddressDetails)
    }

    fun getUserAddresses(userId: String): Flow<List<UserAddressDetails>> {
        return addressDAO.getUserAddresses(userId)
    }

    suspend fun getAddressById(id: Int): UserAddressDetails {
        return addressDAO.getAddressById(id)
    }

    suspend fun updateAddress(userAddressDetails: UserAddressDetails) {
        addressDAO.updateAddress(userAddressDetails)
    }

    suspend fun deleteAddress(userAddressDetails: UserAddressDetails) {
        addressDAO.deleteAddress(userAddressDetails)
    }

    suspend fun updateDefaultAddress(id: Int, userId: String) {
        addressDAO.updateDefaultAddress(id, userId)
    }
}