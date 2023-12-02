// User.kt
package com.example.consume_itunesapi.model

data class User(
    val name: String,
    val age: Int,
    val gender: String,
    val imageUrl: String,
    val country: String, // Add this line
    val email: String // Add this line
)
