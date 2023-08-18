package com.twoup.personalfinance.domain.model.transaction.category

import com.twoup.personalfinance.const.ConstDefaultValue
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseModel(
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val data: CategoryResponseData = CategoryResponseData()
){
    @Serializable
    data class CategoryResponseData(
        val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val name: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val categoryId: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val userId: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    )
}
