package com.twoup.personalfinance.remote.dto.transaction


import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionResponse(
    val data: CreateTransactionResponseData?,
    val message: String? = "",
    val status: Int? = 0
) {
    @Serializable
    data class CreateTransactionResponseData(
        val id: String? = ""
    )
}