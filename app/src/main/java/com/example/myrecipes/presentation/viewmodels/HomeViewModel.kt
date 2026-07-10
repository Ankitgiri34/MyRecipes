package com.example.myrecipes.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipes.data.remote.KtorClient
import com.example.myrecipes.data.remote.RecipeAPIService
import com.example.myrecipes.data.remote.dto.RecipeDTO
import com.example.myrecipes.data.repository.RecipeRepositoryImpl
import com.example.myrecipes.domain.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // State Management ka kaam //

    private val repository: RecipeRepository =
        RecipeRepositoryImpl(apiService = RecipeAPIService(client = KtorClient.client))

    var isLoading by mutableStateOf(false)                // jab tak data fetch ho raha hai tab tak loading dikhane ke liye...
        private set                                                 // isko class ke bahar read only mode ke access kiya ja sakta hai, iski value ko update nahi kia ja sakta class ke bahar

    var errorMessage by mutableStateOf<String?>(null)   // Incase ager kisi wajah se data fetch na ho paye toh error message dikhane ke liye
        private set

    var recipes by mutableStateOf<List<RecipeDTO>>(emptyList())   // Saari recipes fetch karne ke liye, by default empty hoga, ek baar saari recipes fetch ho jayenge toh woh saari isme aa jayengi
        private set

    var categories by mutableStateOf<List<String>>(listOf("All"))     // byDefault all categories hogi
        private set

    var selectedCategory by mutableStateOf("All")        // User jo bhi category select kaega uss category ke according items sort ho jayengi, by default All recipes hongi
        private set

    private var allRecipes: List<RecipeDTO> = emptyList()          // by default empty hogi

    init {
        fetchRecipes()
    }

    fun fetchRecipes() {                                           // iss method ka kaam hai data/recipes ko fetch karna, jab bhi data fetch kerna hoga iss function ko call kar lenge

        isLoading = true                                           // jab tak data fetch ho raha hai tab tak loading dikhane ke liye                                             // jab tak data fetch ho raha hai tab tak loading dikhane ke liye
        errorMessage = null

        viewModelScope.launch {

            try {
                val result = repository.getAllRecipes()
                allRecipes = result

                val cuisines = result.map { it.cuisine }.distinct().sorted()
                categories = listOf("All") + cuisines

                applyFilters()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }

        }
    }

    fun onCategorySelected(category: String) {                   // jis bhi category pe click karenge uss category ke according selected category ko change karne ke liye
        selectedCategory = category
        applyFilters()
    }


    private fun applyFilters() {                               // Categories ke basis pe recipes ko filter karne ke liye

        recipes =
            if (selectedCategory == "All") allRecipes
            else allRecipes.filter { it.cuisine == selectedCategory }
    }
}