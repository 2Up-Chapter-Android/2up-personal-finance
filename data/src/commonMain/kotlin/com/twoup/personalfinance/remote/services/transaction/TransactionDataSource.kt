package com.twoup.personalfinance.remote.services.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel


class TransactionDataSource(private val service: TransactionService) {
    suspend fun createTransaction(createTransactionRequest: CreateTransactionRequestModel) = service.createTransaction(createTransactionRequest)

    suspend fun getListWallets() = service.getListWallets()

    suspend fun getListTransaction() = service.getListTransaction()
}