package com.example.testingdemo.data.remote

import com.example.testingdemo.BuildConfig
import com.example.testingdemo.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun getApiData(
        @Query("q")  searchQuery: String,
        @Query("key")  apiKey: String = BuildConfig.API_KEY,
    ) : Response<ImageResponse>

}