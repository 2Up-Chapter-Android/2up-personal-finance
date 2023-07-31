package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterResponseModel
import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel
import com.twoup.personalfinance.domain.model.category.GetListCategoryResponseModel
import com.twoup.personalfinance.domain.repository.category.CategoryRepository
import com.twoup.personalfinance.local.IDatabase
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.category.CategoryDatSource
import com.twoup.personalfinance.remote.util.networkBoundResource
import com.twoup.personalfinance.remote.util.safeApiCall
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CategoryRepositoryImpl(
    private val dataSource: CategoryDatSource,
    private val database: IDatabase

) : CategoryRepository {

    //    override suspend fun category(categoryRequest: CategoryRequestModel): Result<CategoryResponseModel> {
//        return safeApiCall { dataSource.category(categoryRequest) }.map { it.mapToDomain() }
//    }
    override suspend fun getListCategory(): Flow<Resource<GetListCategoryResponseModel>> {
        return networkBoundResource(
            query = {
                flowOf(GetListCategoryResponseModel(data = database.getAllCategory()))
            },
            fetch = {
                dataSource.getListCategory().map { it.mapToDomain() }
            },
            saveFetchResult = { fetchResult ->
//                database.deleteCategory()
                fetchResult.data?.data?.forEach {
                    database.insertCategory(
                        id = it.id.toString(),
                        name = it.name,
                        categoryId = it.categoryId,
                        userId = it.userID,
                    )
                }
            }
        )
    }

    override suspend fun category(categoryRequest: CategoryRequestModel): Resource<CategoryResponseModel> {
        return safeApiCall { dataSource.category(categoryRequest) }.map { it.mapToDomain() }
    }

}