package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {
    suspend fun getListWallets(): Flow<Resource<GetListWalletResponseModel>>
}