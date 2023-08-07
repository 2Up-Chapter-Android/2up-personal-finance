package com.aicontent.category.presentation.ui

data class CategoryUiState(
    val isLoading: Boolean = false,
    val error: String = "",

    val name: String = "",
    val categoryId: String = "",

    val nameError: String = "",
    val categoryIdError: String = "",

    val accessToken: String = ""
) {
    val enableRegisterButton
        get() = !isLoading &&
                name.isNotBlank() &&
                categoryId.isNotBlank()


    val visibilityNameError get() = nameError.isNotBlank()
    val visibilityCategoryIdError get() = categoryIdError.isNotBlank()
}