package com.twoup.personalfinance.remote.services.category

import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryRequestModel
import com.twoup.personalfinance.remote.dto.authentication.register.RegisterResponse
import com.twoup.personalfinance.remote.dto.category.CategoryResponse
import com.twoup.personalfinance.remote.dto.category.GetListCategoryResponse
import com.twoup.personalfinance.remote.dto.transaction.GetListWalletResponse
import com.twoup.personalfinance.utils.data.Resource
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface CategoryService {

    @POST("categorys")
    suspend fun category(@Body categoryRequest: CategoryRequestModel): Resource<CategoryResponse>
    @GET("categorys")
    suspend fun getListCategory(): Resource<GetListCategoryResponse>

//    @DELETE("categorys/{category_id}")
//    suspend fun deleteCategory(@Body categoryRequest: CategoryRequest): Resource<CategoryResponse>
}