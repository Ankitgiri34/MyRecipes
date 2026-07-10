package com.example.myrecipes.data.repository

import com.example.myrecipes.data.remote.RecipeAPIService
import com.example.myrecipes.data.remote.dto.RecipeDTO
import com.example.myrecipes.domain.repository.RecipeRepository

class RecipeRepositoryImpl (private val apiService: RecipeAPIService) : RecipeRepository {

    override suspend fun getAllRecipes(): List<RecipeDTO> {
        return apiService.getAllRecipes().recipes
    }

    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return apiService.getRecipeById(id)
    }
}