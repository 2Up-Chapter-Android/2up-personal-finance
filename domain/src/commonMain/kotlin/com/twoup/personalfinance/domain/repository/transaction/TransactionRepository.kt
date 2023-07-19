package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.utils.data.Resource


interface TransactionRepository {
    suspend fun getListWallets(): Resource<GetListWalletResponseModel>
}