package com.aicontent.category.presentation.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.navigation.CategorySharedScreen
import com.twoup.personalfinance.utils.data.HttpException
import com.twoup.personalfinance.utils.data.NetworkException
import com.twoup.personalfinance.utils.data.fold
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalAnimationApi::class)
class CategoryScreen : Screen {
    @Composable
    override fun Content() {
//        TransactionDashboardScreen()
        CategoryScreenView()
//    }
    }

    @Composable
    fun CategoryScreenView() {
        val categoryViewModel = rememberScreenModel { CategoryViewModel() }
        val categoryUiState by categoryViewModel.categoryUiState.collectAsState()
        val categoryState = categoryViewModel.categoryState.collectAsState()
        val categoryHttpStatus = rememberScreen(CategorySharedScreen.CategoryHttpStatus)
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(categoryState.value) {
            categoryState.value.fold(
                onSuccess = { categoryData ->
                    val id = categoryData.data
                    val name = categoryData.data
                    val categoryId = categoryData.data
                    val userId = categoryData.data
                    Napier.d(
                        tag = "Test Category",
                        message = "Create Category successful/ ID: $id, Name:$name, CategoryId:$categoryId, UserId:$userId"
                    )
                    navigator.push(categoryHttpStatus)
                },
                onFailure = { error ->
                    when (error) {
                        is HttpException -> {
                            val errorMessage = error.errorMessage.toString()
                            Napier.d(tag = "Test Category", message = errorMessage)
                        }

                        is NetworkException -> {
                            Napier.d(tag = "Test Category", message = "Network error occurred")
                        }

                        else -> {
                            val errorMessage = error.errorMessage.toString()
                            Napier.d(tag = "Test Category", message = errorMessage)
                        }
                    }
                }
            )
        }
//        Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hmmm ")
//                 Name TextField
            TextField(
                value = categoryUiState.name,
                onValueChange = { categoryViewModel.onNameValueChange(it) },
                label = { Text("Name") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White, // Change the background color
                    cursorColor = Color.Black, // Change the cursor color
                    textColor = Color.Black, // Change the text color
                    disabledTextColor = Color.Gray, // Change the text color when disabled
                    focusedIndicatorColor = Color.Blue, // Change the color of the focused indicator
                    unfocusedIndicatorColor = Color.Gray, // Change the color of the unfocused indicator
                    disabledIndicatorColor = Color.LightGray, // Change the color of the disabled indicator
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(bottom = 8.dp)
            )

//                 Category ID TextField
            TextField(
                value = categoryUiState.categoryId,
                onValueChange = { categoryViewModel.onCategoryIdValueChange(it) },
                label = { Text("Category ID") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White, // Change the background color
                    cursorColor = Color.Black, // Change the cursor color
                    textColor = Color.Black, // Change the text color
                    disabledTextColor = Color.Gray, // Change the text color when disabled
                    focusedIndicatorColor = Color.Blue, // Change the color of the focused indicator
                    unfocusedIndicatorColor = Color.Gray, // Change the color of the unfocused indicator
                    disabledIndicatorColor = Color.LightGray, // Change the color of the disabled indicator
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(bottom = 8.dp)
            )

//                 Name error message
//            if (categoryUiState.visibilityNameError) {
            Text(
                text = categoryUiState.nameError,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
//            }

//                 Category ID error message
//            if (categoryUiState.visibilityCategoryIdError) {
            Text(
                text = categoryUiState.categoryIdError,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
//            }

//                 Register button with custom styling and animation
            Button(
                onClick = {
                    categoryViewModel.createCategory()
                },
                enabled = categoryUiState.enableRegisterButton,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .animateContentSize()
                    .padding(start = 32.dp, end = 32.dp),
                content = {
                    Text(
                        text = "Add Category",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}