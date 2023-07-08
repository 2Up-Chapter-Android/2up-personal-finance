package com.twoup.personalfinance.domain.model.transaction.createTrans

import com.twoup.personalfinance.const.ConstDefaultValue
import kotlinx.serialization.Serializable


@Serializable
data class CreateTransactionResponseModel(
    val data: CreateTransactionResponseData = CreateTransactionResponseData(),
    val message: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val status: Int = 0
){
    @Serializable
    data class CreateTransactionResponseData(val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING)
}
