package com.example.myrecipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeRequest (          //jab bhi hame koi new data server per bhejna hoga toh ham iss class ke format me usko pack karke bhejenge

    val caloriesPerServing: Int,
    val cookTimeMinutes: Int,
    val cuisine: String,
    val difficulty: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val mealType: List<String>,
    val name: String,
    val prepTimeMinutes: Int,
    val servings: Int,
    val tags: List<String>,
) {
}