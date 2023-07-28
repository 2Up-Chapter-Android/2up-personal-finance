package com.twoup.personalfinance.remote.services.category

import com.twoup.personalfinance.domain.model.category.CategoryRequestModel

class CategoryDatSource(private val service : CategoryService) {
    suspend fun category(categoryRequest: CategoryRequestModel) = service.category(categoryRequest)
    suspend fun getListCategorys() = service.getListCategory()

}

interface NoteLocalDataSource{
    suspend fun insertCategory(categoryRequestModel: CategoryRequestModel)
    suspend fun getAllCategory(): List<CategoryRequestModel>
    suspend fun removeAllNote()

}