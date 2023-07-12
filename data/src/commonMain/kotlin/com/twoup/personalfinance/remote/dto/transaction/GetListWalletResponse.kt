package com.twoup.personalfinance.remote.dto.transaction


import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.WalletGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetListWalletResponse(
    @SerialName("data")
    val data: List<GetListWalletData>? = listOf(),
    @SerialName("status")
    val status: Int? = 0,
    @SerialName("status_message")
    val statusMessage: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
    @SerialName("timestamp")
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
){
    @Serializable
    data class GetListWalletData(
        @SerialName("amount")
        val amount: Int? = 0,
        @SerialName("description")
        val description: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("id")
        val id: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("name")
        val name: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
        @SerialName("wallet_group")
        val walletGroup: WalletGroup? = WalletGroup.OTHER
    )
}