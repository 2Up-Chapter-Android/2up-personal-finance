package com.twoup.personalfinance.domain.model.transaction.createTrans


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionRequestModel(
    @SerialName("amount")
    val amount: Int,
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("description")
    val description: String,
    @SerialName("note")
    val note: String,
    @SerialName("type")
    val type: String,
    @SerialName("wallet_id")
    val walletId: String
)