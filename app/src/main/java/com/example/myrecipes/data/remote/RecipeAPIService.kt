package com.example.myrecipes.data.remote

import androidx.compose.ui.autofill.ContentType
import com.example.myrecipes.data.remote.dto.AddRecipeRequest
import com.example.myrecipes.data.remote.dto.RecipeDTO
import com.example.myrecipes.data.remote.dto.RecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType

class RecipeAPIService (private val client: HttpClient){      // API Service ek class hoti hai jisme ham API related saare functions likhte hai (In simple = API Manager)

    suspend fun getAllRecipes(): RecipeResponse{             // All recipes ko fetch karne ke liye function
        return client.get(urlString = "${
            KtorClient.BASE_URL}recipes").body()            // get() hame server tak le jata hai waha jo bhi json milega usko ye automatically model me convert kar dega & .body() response ko hamare model ke according convert kar dega
    }

    suspend fun getRecipeById(id: Int): RecipeDTO{        // Single recipe ko fetch karne ke liye function
        return client.get(urlString = "${KtorClient.BASE_URL}recipes/$id").body()
    }

    //For adding Own recipe on server
    suspend fun addRecipe(request: AddRecipeRequest){
        client.post(urlString = "${KtorClient.BASE_URL}recipes/add"){     //post() tab use hota hai jab ham server per data bhejne hai
            contentType(io.ktor.http.ContentType.Application.Json)       // Application se Json format me data bhej rahe hai
            setBody(request)                                            // setBody() hamare kotlin object ko json format me convert kar dega
        }
    }
}