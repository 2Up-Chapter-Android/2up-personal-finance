package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.category.GetListCategoryResponseModel
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.remote.dto.category.CategoryResponse
import com.twoup.personalfinance.remote.dto.category.GetListCategoryResponse
import com.twoup.personalfinance.remote.dto.transaction.GetListWalletResponse


fun GetListCategoryResponse?.mapToDomain() = GetListCategoryResponseModel(
    status = this?.status!!,
    statusMessage = null.toString(),
    timestamp = this.timestamp,
    data = this.data?.map { it.mapToDomain() } ?: listOf()
)

fun GetListCategoryResponse.GetListCategoryData?.mapToDomain() =
    Category(
        id = this?.id,
        name = this?.name.toString(),
        categoryId = this?.categoryId.toString(),
        userID = this?.userId.toString()
    )
