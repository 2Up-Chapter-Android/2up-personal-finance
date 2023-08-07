package com.twoup.personalfinance.domain.model.category

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.Category

data class GetListCategoryResponseModel(
    val data: List<Category> = listOf(),
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)
