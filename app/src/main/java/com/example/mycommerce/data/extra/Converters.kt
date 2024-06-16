package com.example.mycommerce.data.extra

import androidx.room.TypeConverter
import com.example.mycommerce.data.models.ECommerceItem
import com.example.mycommerce.data.models.UserAddressDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

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

    @TypeConverter
    fun fromECommerceItemList(value: List<ECommerceItem>?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toECommerceItemList(value: String): List<ECommerceItem> {
        val gson = Gson()
        val type = object : TypeToken<List<ECommerceItem>>() {}.type
        return gson.fromJson(value, type)
    }

}
