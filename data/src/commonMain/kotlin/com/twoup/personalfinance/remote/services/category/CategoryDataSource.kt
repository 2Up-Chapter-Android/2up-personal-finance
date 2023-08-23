package com.twoup.personalfinance.remote.services.category

import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryRequestModel

class CategoryDatSource(private val service : CategoryService) {
    suspend fun category(categoryRequest: CategoryRequestModel) = service.category(categoryRequest)
    suspend fun getListCategory() = service.getListCategory()

}

interface NoteLocalDataSource{
    suspend fun insertCategory(categoryRequestModel: CategoryRequestModel)
    suspend fun getAllCategory(): List<CategoryRequestModel>
    suspend fun removeAllNote()

}