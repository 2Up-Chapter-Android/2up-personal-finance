package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import com.twoup.personalfinance.domain.model.transaction.TransactionType
import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.remote.dto.transaction.GetAllTransactionsResponse
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

fun GetAllTransactionsResponse?.mapToDomain() = GetAllTransactionsResponseModel(
    status = this?.status ?: 0,
    statusMessage = this?.statusMessage ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    timestamp = this?.timestamp ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    data = this?.data?.map { it.mapToDomain() } ?: listOf()
)

fun GetAllTransactionsResponse.Data?.mapToDomain() = TransactionEntity(
    amount = this?.amount ?: 0,
    categoryId = this?.categoryId ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    description = this?.description ?: "",
    id = this?.id ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    walletId = this?.walletId ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    createdAt = this?.createdAt ?: 0,
    note = this?.note ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
    updatedAt = this?.updatedAt ?: 0,
    type = this?.type ?: TransactionType.DEFAULT
)