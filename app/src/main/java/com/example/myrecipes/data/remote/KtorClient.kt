package com.example.myrecipes.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {                           //KtorClient, internet se communicate karne wala banda hota hai (ye request bhejega or response receive karega)

    val client = HttpClient(Android){
        install(ContentNegotiation){
            json(
                Json{
                    ignoreUnknownKeys = true     // If Json se kuch extra data aata hai jo hamare model me available nhi hai toh ye unhe ignore kar dega
                }
            )
        }
    }

    const val BASE_URL = "https://dummyjson.com/"       //Base url
//    const val RECIPE = "$BASE_URL/recipes"           // End point
}