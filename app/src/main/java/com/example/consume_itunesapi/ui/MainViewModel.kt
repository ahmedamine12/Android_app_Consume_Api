package com.example.consume_itunesapi.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consume_itunesapi.RandomUserService
import com.example.consume_itunesapi.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    private val randomUserService: RandomUserService by lazy { createRandomUserService() }

    // Simulated data
    var users by mutableStateOf<List<User>>(emptyList())
        private set

    fun fetchRandomUsers(count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = randomUserService.getRandomUsers(count)

                if (response.isSuccessful) {
                    val userResponses = response.body()?.results.orEmpty()
                    users = userResponses.map {
                        User(
                            name = " ${it.name.first} ${it.name.last}",
                            age = it.dob.age,
                            gender = it.gender,
                            imageUrl = it.picture.large,
                            country = it.location.country,
                            email = it.email
                        )
                    }

                } else {
                    // Log the error response
                    Log.e("MainViewModel", "API request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Log exceptions
                Log.e("MainViewModel", "Exception during API request", e)
            }
        }
    }

    private fun createRandomUserService(): RandomUserService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(RandomUserService::class.java)
    }
}
