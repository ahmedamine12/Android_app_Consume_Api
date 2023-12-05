// Import necessary libraries and classes
package com.example.consume_itunesapi

import MainViewModel
import com.example.consume_itunesapi.model.User
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.consume_itunesapi.ui.theme.Consume_ItunesApiTheme

// Data classes representing the structure of the API response
data class RandomUserResponse(
    val results: List<UserResponse>,
    val info: Info
)

data class Info(
    val results: Int,
    val page: Int,
    val version: String
)

data class UserResponse(
    val name: Name,
    val dob: Dob,
    val gender: String,
    val picture: Picture,
    val location: Location,
    val email: String
)

data class Location(
    val country: String
)

data class Name(
    val first: String,
    val last: String
)

data class Dob(
    val age: Int
)

data class Picture(
    val large: String
)

// MainActivity class representing the main activity of the app
class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()

    // Function called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content of the activity using Compose
        setContent {
            Consume_ItunesApiTheme {
                // Surface is a basic material design container
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display user information using the Composable function
                    DisplayUserInformation(viewModel.users)
                }
            }
        }

        // Trigger the fetch when the activity is created
        viewModel.fetchRandomUsers(count = 5) // Adjust the count as needed
    }

}

// Composable function to display a list of users using LazyColumn
@Composable
fun DisplayUserInformation(users: List<User>) {
    LazyColumn {
        // Iterate through the list of users and display user information
        items(users) { user ->
            DisplayUserInformation(user = user)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Composable function to display individual user information
@Composable
fun DisplayUserInformation(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the user's image using Coil's rememberImagePainter
        Image(
            painter = rememberImagePainter(data = user.imageUrl),
            contentDescription = "User Image",
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Display user information such as name, gender, age, country, and email
        Text(text = "Name: ${user.name}")
        Text(text = "Gender: ${user.gender}")
        Text(text = "Age: ${user.age}")
        Text(text = "Country: ${user.country}") // Display country
        Text(text = "Email: ${user.email}") // Display email
    }
}

