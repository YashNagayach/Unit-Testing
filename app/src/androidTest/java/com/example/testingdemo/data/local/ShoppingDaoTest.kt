package com.example.testingdemo.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testingdemo.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingItemDatabase: ShoppingItemDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setUp() {
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        shoppingDao = shoppingItemDatabase.shoppingDao()
    }

    @After
    fun teardown() {
        shoppingItemDatabase.close()
    }


    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url")
        shoppingDao.insertShoppingItem(shoppingItem)

        val list = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertTrue(list.contains(shoppingItem))
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem1 = ShoppingItem("name", 1, 1f, "url")
        val shoppingItem2 = ShoppingItem("name2", 2, 2f, "url2")
        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)

        shoppingDao.deleteShoppingItem(shoppingItem1)

        val list = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertFalse(list.contains(shoppingItem1))
    }
}