# Android_app_Consume_Api

**Technologies Used**
Jetpack Compose
Retrofit
Moshi

**Code Overview**

- MainViewModel
Manages UI-related data and business logic.
Fetches random user data from the API using Retrofit.

- MainActivity
Main entry point of the app.
Sets up Compose UI and triggers data fetching.

- Compose UI
DisplayUserInformation composable for displaying user information.
Individual composable for displaying an individual user.

- Retrofit and Moshi
Configurations for making API requests and parsing JSON.

- Data Classes
Represent the structure of JSON data returned by the API.

- RandomUserService
Retrofit interface for making API requests.
