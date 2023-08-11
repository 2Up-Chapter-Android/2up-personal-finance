package com.twoup.personalfinance.remote.dto.transaction


import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.TransactionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllTransactionsResponse(
    @SerialName("data")
    val data: List<Data>? = listOf(),
    @SerialName("status")
    val status: Int? = 0,
    @SerialName("status_message")
    val statusMessage: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
    @SerialName("timestamp")
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
){
    @Serializable
    data class Data(
        @SerialName("amount")
        val amount: Int? = 0,
        @SerialName("category_id")
        val categoryId: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("created_at")
        val createdAt: Long? = 0,
        @SerialName("description")
        val description: String? = "",
        @SerialName("id")
        val id: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("note")
        val note: String? = "",
        @SerialName("type")
        val type: TransactionType? = TransactionType.DEFAULT,
        @SerialName("updated_at")
        val updatedAt: Long? = 0,
        @SerialName("wallet_id")
        val walletId: String? = ConstDefaultValue.DEFAULT_VALUE_STRING
    )
}