package com.example.myrecipes.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myrecipes.presentation.components.ErrorMessage
import com.example.myrecipes.presentation.components.LoadingIndicator
import com.example.myrecipes.presentation.viewmodels.HomeViewModel
import com.example.myrecipes.ui.theme.MyOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRecipeClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recipes",
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
            )
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MyOrange.copy(alpha = 0.02f))
                .padding(innerPadding)
        ) {
            when {
                viewModel.isLoading -> LoadingIndicator(strokeWidth = 1.dp)

                viewModel.errorMessage != null -> ErrorMessage(
                    errorMessage = viewModel.errorMessage!!,
                    onRetry = { viewModel.fetchRecipes() }
                )

                else -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                            HomeHeader()
                        }

                        if (viewModel.categories.size > 1) {
                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                CategorySection(
                                    categories = viewModel.categories,
                                    selected = viewModel.selectedCategory,
                                    onSelected = viewModel::onCategorySelected
                                )
                            }
                        }
                        item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                            SectionHeader(
                                title = if (viewModel.selectedCategory == "All") "All Recipes" else viewModel.selectedCategory,
                                icon = Icons.Default.MenuOpen
                            )
                        }
                        if (viewModel.recipes.isEmpty()) {
                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {

                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No recipes found",              // ager server pe koi recipes hi na mile toh ye message dikhayega
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MyOrange
                                    )
                                }
                            }
                        } else {
                            items(viewModel.recipes, key = { it.id }) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onClick = { onRecipeClick(recipe.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}