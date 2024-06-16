package com.example.mycommerce.data.extra

import androidx.room.TypeConverter
import com.example.mycommerce.data.models.ECommerceItem
import com.example.mycommerce.data.models.UserAddressDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromString(value: String): List<ECommerceItem> {
        val listType = object : TypeToken<List<ECommerceItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ECommerceItem>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromUserAddressDetails(value: UserAddressDetails?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toUserAddressDetails(value: String): UserAddressDetails {
        val gson = Gson()
        val type = object : TypeToken<UserAddressDetails>() {}.type
        return gson.fromJson(value, type)
    }

}
