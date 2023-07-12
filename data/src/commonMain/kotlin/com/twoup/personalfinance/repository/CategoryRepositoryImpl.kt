package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel
import com.twoup.personalfinance.domain.repository.category.CategoryRepository
import com.twoup.personalfinance.local.SecureStorageWrapper
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.category.CategoryDataSource
import com.twoup.personalfinance.remote.util.safeApiCall

class CategoryRepositoryImpl(
    private val dataSource : CategoryDataSource,
    private val secureStorageWrapperImpl: SecureStorageWrapper
) : CategoryRepository {

    override suspend fun category(categoryRequest: CategoryRequestModel): Result<CategoryResponseModel> {
        return safeApiCall { dataSource.category(categoryRequest) }.map { it.mapToDomain() }
    }

}

