// Import necessary libraries and classes
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consume_itunesapi.api.RandomUserService
import com.example.consume_itunesapi.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// MainViewModel class responsible for managing UI-related data
class MainViewModel : ViewModel() {

    // Lazily initialize the RandomUserService using createRandomUserService function
    private val randomUserService: RandomUserService by lazy { createRandomUserService() }

    // Simulated data representing a list of users
    var users by mutableStateOf<List<User>>(emptyList())
        private set

    // Function to fetch random users from the API
    fun fetchRandomUsers(count: Int) {
        // Launch a coroutine in the viewModelScope on the IO dispatcher

        /*
         viewModelScope.launch est utilisé pour lancer une nouvelle coroutine liée
         au cycle de vie du ViewModel. Elle s'exécute sur le thread Dispatchers.IO,
         optimisé pour les opérations d'entrée/sortie.

         */
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make a network request to get random user data
                /*
                RandomUserService.getRandomUsers est une fonction suspendue.
                Elle peut être suspendue sans bloquer le thread
                jusqu'à ce que le résultat soit disponible.
                Cela permet des interactions fluides et réactives avec l'UI
                 */
                val response = randomUserService.getRandomUsers(count)

                // Check if the API request was successful
                if (response.isSuccessful) {
                    // Extract user responses from the API response, or use an empty list if null
                    val userResponses = response.body()?.results.orEmpty()

                    // Map user responses to User model and update the users property
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
                    // Log the error response code if the API request fails
                    Log.e("MainViewModel", "API request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Log exceptions that occur during the API request
                Log.e("MainViewModel", "Exception during API request", e)
            }
        }
    }

    // Function to create and return an instance of RandomUserService
    private fun createRandomUserService(): RandomUserService {
        // Build Moshi for JSON parsing
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // Build Retrofit instance for making network requests
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Create and return RandomUserService using Retrofit
        return retrofit.create(RandomUserService::class.java)
    }
}
