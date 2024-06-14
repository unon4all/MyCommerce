package com.example.mycommerce.data.localDatabase.repository

import com.example.mycommerce.data.localDatabase.dao.ECommerceItemDAO
import com.example.mycommerce.data.localDatabase.models.ECommerceItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ECommerceItemRepository @Inject constructor(private val eCommerceItemDao: ECommerceItemDAO) {

    /**
     * Retrieves a selected item by its ID.
     * @param itemId ID of the item to retrieve.
     * @return Flow representing the selected item.
     */
    suspend fun getItem(itemId: String): Flow<ECommerceItem?> {
        return eCommerceItemDao.getItem(itemId)
    }

    /**
     * Adds a new item to the database.
     * @param item The item to add.
     */
    suspend fun addItem(item: ECommerceItem) {
        eCommerceItemDao.insertItem(item)
    }
}
