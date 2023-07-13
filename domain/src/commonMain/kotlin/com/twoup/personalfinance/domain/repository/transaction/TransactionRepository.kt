package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel


interface TransactionRepository {
    suspend fun getListWallets(): Result<GetListWalletResponseModel>
}