package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.WalletGroup
import com.twoup.personalfinance.domain.model.transaction.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.remote.dto.transaction.GetListWalletResponse

fun GetListWalletResponse?.mapToDomain() = GetListWalletResponseModel(
    status = this?.status ?: 0,
    statusMessage = this?.statusMessage ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    timestamp = this?.timestamp ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    data = this?.data?.map { it.mapToDomain() } ?: listOf(GetListWalletResponseModel.GetListWalletData())
)

fun GetListWalletResponse.GetListWalletData?.mapToDomain() =
    GetListWalletResponseModel.GetListWalletData(
        amount = this?.amount ?: 0,
        description = this?.description ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        id = this?.id ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        name = this?.name ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        walletGroup = this?.walletGroup ?: WalletGroup.OTHER
    )