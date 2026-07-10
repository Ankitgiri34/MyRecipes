package com.example.myrecipes.presentation.screens.recipe_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myrecipes.presentation.components.ErrorMessage
import com.example.myrecipes.presentation.components.LoadingIndicator
import com.example.myrecipes.presentation.components.MyTopbar
import com.example.myrecipes.presentation.viewmodels.RecipeDetailViewModel
import com.example.myrecipes.ui.theme.MyOrange

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = viewModel()
){

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetail(recipeId)
    }

    Scaffold(
        topBar = {
            MyTopbar(
                title = "Recipe Details",
                onBackClick = onBack,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = MyOrange.copy(alpha = 0.02f))
        ) {

            when{

                viewModel.isLoading -> LoadingIndicator(strokeWidth = 1.dp)
                viewModel.errorMessage != null -> ErrorMessage(
                    errorMessage = viewModel.errorMessage!!,
                    onRetry = {viewModel.fetchRecipeDetail(recipeId)}
                )
                viewModel.recipe != null -> {

                    RecipeDetailContent(details = viewModel.recipe!!)
                }
            }
        }
    }
}