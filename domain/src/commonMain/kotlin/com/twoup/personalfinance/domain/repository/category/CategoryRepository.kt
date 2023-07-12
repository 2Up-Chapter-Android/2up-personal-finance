package com.twoup.personalfinance.domain.repository.category

import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel

interface CategoryRepository {
    suspend fun category(categoryRequest: CategoryRequestModel): Result<CategoryResponseModel>

}