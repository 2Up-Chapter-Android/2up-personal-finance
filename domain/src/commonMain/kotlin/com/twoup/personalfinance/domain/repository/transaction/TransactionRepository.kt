package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.transaction.getWallet.GetListWalletResponseModel


interface TransactionRepository {
    suspend fun getListWallets(): Result<GetListWalletResponseModel>
}