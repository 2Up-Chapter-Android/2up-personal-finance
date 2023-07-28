package com.twoup.personalfinance.domain.repository.category

import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.category.CategoryRequestModel
import com.twoup.personalfinance.domain.model.category.GetListCategoryResponseModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun category(categoryRequest: CategoryRequestModel): Result<CategoryResponseModel>

    suspend fun getListCategorys(): Flow<Resource<GetListCategoryResponseModel>>

}