package com.example.testingdemo.repository

import androidx.lifecycle.LiveData
import com.example.testingdemo.data.local.ShoppingDao
import com.example.testingdemo.data.local.ShoppingItem
import com.example.testingdemo.data.remote.PixabayApi
import com.example.testingdemo.data.remote.responses.ImageResponse
import com.example.testingdemo.utils.Resource

class ShoppingRepositoryImpl(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchPixabayImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayApi.getApiData(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("Something went wrong", null)
            } else {
                Resource.error("Something went wrong", null)
            }
        } catch (e: Exception) {
            Resource.error("Internet Issues", null)
        }
    }
}