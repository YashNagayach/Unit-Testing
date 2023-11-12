package com.example.testingdemo.di

import android.content.Context
import androidx.room.Room
import com.example.testingdemo.data.local.ShoppingDao
import com.example.testingdemo.data.local.ShoppingItemDatabase
import com.example.testingdemo.data.remote.PixabayApi
import com.example.testingdemo.repository.ShoppingRepository
import com.example.testingdemo.repository.ShoppingRepositoryImpl
import com.example.testingdemo.utils.Constant.BASE_URL
import com.example.testingdemo.utils.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context, ShoppingItemDatabase::class.java, DATABASE_NAME
    ).build()


    @Provides
    @Singleton
    fun provicesShoppingRepo(
        dao: ShoppingDao,
        api: PixabayApi
    ) : ShoppingRepository {
        return ShoppingRepositoryImpl(dao, api)
    }

    @Provides
    @Singleton
    fun providesDao(shoppingItemDatabase: ShoppingItemDatabase) = shoppingItemDatabase.shoppingDao()

    @Provides
    @Singleton
    fun providesPixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayApi::class.java)
}