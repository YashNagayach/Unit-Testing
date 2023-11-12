package com.example.testingdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testingdemo.MainCoroutineRule
import com.example.getOrAwaitValue
import com.example.testingdemo.repository.FakeShoppingRepositoryImplTest
import com.example.testingdemo.utils.Constant
import com.example.testingdemo.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var shoppingViewModel: ShoppingViewModel


    @Before
    fun setup() {
        shoppingViewModel = ShoppingViewModel(
            FakeShoppingRepositoryImplTest(),
        )
    }

    @Test
    fun `insert shopping item with empty field returns error`() {
        shoppingViewModel.insertShoppingItem("name", "", "2")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_NAME_LENGTH + 1) {
                append(i)
            }
        }
        shoppingViewModel.insertShoppingItem(string, "5", "2")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price length, returns error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH + 1) {
                append(i)
            }
        }
        shoppingViewModel.insertShoppingItem("name", "5", string)
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {
        shoppingViewModel.insertShoppingItem("name", "8423984729847239847298479283", "2")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid data, returns success`() {
        shoppingViewModel.insertShoppingItem("name", "8", "2")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}