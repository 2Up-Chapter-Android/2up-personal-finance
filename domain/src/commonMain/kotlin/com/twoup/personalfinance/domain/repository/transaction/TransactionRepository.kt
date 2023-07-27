package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionResponseModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel


interface TransactionRepository {
    suspend fun getListWallets(): Result<GetListWalletResponseModel>
    suspend fun createTransaction(createTransactionRequestModel: CreateTransactionRequestModel): Result<CreateTransactionResponseModel>
}