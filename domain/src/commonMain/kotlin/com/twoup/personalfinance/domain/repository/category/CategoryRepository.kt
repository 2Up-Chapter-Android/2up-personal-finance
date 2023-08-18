package com.twoup.personalfinance.domain.repository.category

import com.twoup.personalfinance.domain.model.transaction.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryRequestModel
import com.twoup.personalfinance.domain.model.transaction.category.GetListCategoryResponseModel
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun category(categoryRequest: CategoryRequestModel): Resource<CategoryResponseModel>

    suspend fun getListCategory(): Flow<Resource<GetListCategoryResponseModel>>

}