package com.example.consume_itunesapi.api

import com.example.consume_itunesapi.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService
{
    @GET("api/")
    suspend fun getRandomUsers(@Query("results") count: Int): Response<RandomUserResponse>
}