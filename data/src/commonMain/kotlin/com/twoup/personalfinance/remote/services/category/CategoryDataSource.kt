package com.twoup.personalfinance.remote.services.category

import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel

class CategoryDataSource(private val service : CategoryService) {
    suspend fun category(categoryRequest: CategoryRequestModel) = service.category(categoryRequest)

}