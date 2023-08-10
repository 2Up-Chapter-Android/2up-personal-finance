package com.twoup.personalfinance.remote.dto.transaction


import com.twoup.personalfinance.const.ConstDefaultValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionResponse(
    @SerialName("data")
    val data: CreateTransactionResponseData?,
    @SerialName("status")
    val status: Int? = 0,
    @SerialName("status_message")
    val statusMessage: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
    @SerialName("timestamp")
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
) {
    @Serializable
    data class CreateTransactionResponseData(
        @SerialName("amount")
        val amount: Int? = 0,
        @SerialName("category_id")
        val categoryId: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("created_at")
        val createdAt: Long? = 0,
        @SerialName("description")
        val description: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("id")
        val id: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("note")
        val note: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("type")
        val type: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("updated_at")
        val updatedAt: Long? = 0,
        @SerialName("wallet_id")
        val walletId: String? = ConstDefaultValue.DEFAULT_VALUE_STRING
    )
}