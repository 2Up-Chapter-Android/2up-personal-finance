package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.remote.dto.category.CategoryResponse


fun CategoryResponse?.mapToDomain(): CategoryResponseModel {
    return this?.let {
        CategoryResponseModel(
            status = status,
            statusMessage = null,
            timestamp = timestamp,
            data = data.mapToDomain()
        )
    } ?: CategoryResponseModel()
}

fun CategoryResponse.Data?.mapToDomain(): CategoryResponseModel.CategoryInformation {
    return this?.let {
        CategoryResponseModel.CategoryInformation(
            id = id,
            name = name,
            categoryId = categoryId,
            userId = userId
        )
    } ?: CategoryResponseModel.CategoryInformation()
}