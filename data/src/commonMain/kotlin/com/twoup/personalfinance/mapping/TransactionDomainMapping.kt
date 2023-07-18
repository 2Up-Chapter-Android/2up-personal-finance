package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.transaction.WalletGroup
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionResponseModel
import com.twoup.personalfinance.domain.model.transaction.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.remote.dto.transaction.CreateTransactionResponse
import com.twoup.personalfinance.remote.dto.transaction.GetListWalletResponse

fun GetListWalletResponse?.mapToDomain() = GetListWalletResponseModel(
    status = this?.status ?: 0,
    statusMessage = this?.statusMessage ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    timestamp = this?.timestamp ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    data = this?.data?.map { it.mapToDomain() } ?: listOf()
)

fun GetListWalletResponse.GetListWalletData?.mapToDomain() =
    Wallet(
        amount = this?.amount ?: 0,
        description = this?.description ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        id = this?.id ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        name = this?.name ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        walletGroup = this?.walletGroup ?: WalletGroup.OTHER
    )

fun CreateTransactionResponse?.mapToDomain() = CreateTransactionResponseModel(
    status = this?.status ?: 0,
    statusMessage = this?.statusMessage ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    timestamp = this?.timestamp ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    data = this?.data?.mapToDomain()
        ?: CreateTransactionResponseModel.CreateTransactionResponseData()
)

fun CreateTransactionResponse.CreateTransactionResponseData?.mapToDomain() =
    CreateTransactionResponseModel.CreateTransactionResponseData(
        categoryId = this?.categoryId ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        createdAt = this?.createdAt ?: 0,
        description = this?.description ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        id = this?.id ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        note = this?.note ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        type = this?.type ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        updatedAt = this?.updatedAt ?: 0,
        walletId = this?.walletId ?: ConstDefaultValue.DEFAULT_VALUE_STRING
    )