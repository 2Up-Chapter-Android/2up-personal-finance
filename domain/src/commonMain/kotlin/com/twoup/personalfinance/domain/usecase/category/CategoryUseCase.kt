package com.twoup.personalfinance.domain.usecase.category

import com.twoup.personalfinance.domain.model.transaction.category.CategoryRequestModel
import com.twoup.personalfinance.domain.repository.category.CategoryRepository

class CategoryUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(categoryRequest: CategoryRequestModel) =
        repository.category(categoryRequest)
    suspend fun invokeCategory() = repository.getListCategory()
}