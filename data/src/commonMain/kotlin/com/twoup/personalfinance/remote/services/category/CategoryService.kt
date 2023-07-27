package com.twoup.personalfinance.remote.services.category

import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel
import com.twoup.personalfinance.remote.dto.authentication.register.RegisterResponse
import com.twoup.personalfinance.remote.dto.category.CategoryResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface CategoryService {

    @POST("categorys")
    suspend fun category(@Body categoryRequest: CategoryRequestModel): Result<CategoryResponse>

//    @DELETE("categorys/{category_id}")
//    suspend fun deleteCategory(@Body categoryRequest: CategoryRequest): Resource<CategoryResponse>
}