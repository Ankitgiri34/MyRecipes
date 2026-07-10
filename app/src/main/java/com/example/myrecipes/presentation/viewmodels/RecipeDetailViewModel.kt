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

class RecipeDetailViewModel : ViewModel() {                                       //Iska kaam hai kisi specific recipe ki details ko fetch karna

    private val repository: RecipeRepository =
        RecipeRepositoryImpl(apiService = RecipeAPIService(KtorClient.client))     // iski help se source se data fetch hogi

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var recipe by mutableStateOf<RecipeDTO?>(null)

    fun fetchRecipeDetail(recipeId: Int) {                                  // Iss function ki help se specific id wale Recipe ke details fetch ho jayengi
        isLoading = true
        errorMessage = null

        try {                                                             // iska use exception handling karne ke liye karte hai, try ko koi expected error mil jaye toh catch block execute hoga
            viewModelScope.launch {
                recipe = repository.getRecipeById(recipeId)              // Repository se specific Id wla data mangwa rahe hai
            }
        }catch (e: Exception) {                                         // try ko koi expected error mil jaye toh catch block execute hoga
            errorMessage = e.message ?: "Unknown error occurred"
        }finally {                                                     // finally try & catch dono cases me work karega...
            isLoading = false                                         // aur loading ruk jayegi
        }
    }
}