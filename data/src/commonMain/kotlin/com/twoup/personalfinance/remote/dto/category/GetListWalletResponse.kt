package com.twoup.personalfinance.remote.dto.category

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.WalletGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetListCategoryResponse(
    @SerialName("data")
    val data: List<GetListCategoryData>? = listOf(),
    @SerialName("status")
    val status: Int? = 0,
    @SerialName("status_message")
    val statusMessage: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
    @SerialName("timestamp")
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
){
    @Serializable
    data class GetListCategoryData(
        @SerialName("id")
        val id: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("name")
        val name: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("categoryId")
        val categoryId : String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("userId")
        val userId: String? = ConstDefaultValue.DEFAULT_VALUE_STRING
    )
}