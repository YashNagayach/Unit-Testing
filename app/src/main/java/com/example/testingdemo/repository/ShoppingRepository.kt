package com.example.testingdemo.repository

import androidx.lifecycle.LiveData
import com.example.testingdemo.data.local.ShoppingItem
import com.example.testingdemo.data.remote.responses.ImageResponse
import com.example.testingdemo.utils.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchPixabayImage(imageQuery: String): Resource<ImageResponse>
}