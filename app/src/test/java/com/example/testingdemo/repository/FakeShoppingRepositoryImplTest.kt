package com.example.testingdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testingdemo.data.local.ShoppingItem
import com.example.testingdemo.data.remote.responses.ImageResponse
import com.example.testingdemo.utils.Resource

class FakeShoppingRepositoryImplTest : ShoppingRepository {

    private var isNetworkError: Boolean = false
    private var shoppingItems = mutableListOf<ShoppingItem>()
    private var observableShoppingItem = MutableLiveData<List<ShoppingItem>>()
    private var observableTotolPrice = MutableLiveData<Float>()

    fun setIsNetworkError(value: Boolean) {
        isNetworkError = value
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        observableShoppingItem.postValue(shoppingItems)
        observableTotolPrice.postValue(getTotalPrice())

    }

    private fun getTotalPrice(): Float? {
        return shoppingItems.sumOf {
            it.price.toDouble()
        }.toFloat()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        observableShoppingItem.postValue(shoppingItems)
        observableTotolPrice.postValue(getTotalPrice())
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotolPrice
    }

    override suspend fun searchPixabayImage(imageQuery: String): Resource<ImageResponse> {
        return if (isNetworkError) {
            Resource.error("Is Network Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}

