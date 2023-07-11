package com.twoup.personalfinance.domain.model.transaction.getWallet

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.WalletGroup

data class GetListWalletResponseModel(
    val data: List<GetListWalletData> = listOf(),
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
) {
    data class GetListWalletData(
        val amount: Int = 0,
        val description: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val name: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val walletGroup: WalletGroup = WalletGroup.OTHER
    )
}
