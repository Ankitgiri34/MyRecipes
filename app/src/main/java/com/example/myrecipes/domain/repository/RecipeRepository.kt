package com.example.myrecipes.domain.repository

import com.example.myrecipes.data.remote.dto.RecipeDTO

interface RecipeRepository {       // data fetch karne ke liye jo bhi function/methods required hai unhe yaha define karenge

    suspend fun getAllRecipes(): List<RecipeDTO>

    suspend fun getRecipeById(id: Int): RecipeDTO
}