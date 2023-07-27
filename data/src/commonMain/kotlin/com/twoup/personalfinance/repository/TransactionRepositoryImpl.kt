package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionResponseModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource
import com.twoup.personalfinance.remote.util.safeApiCall

class TransactionRepositoryImpl(private val dataSource: TransactionDataSource) : TransactionRepository {
    override suspend fun getListWallets(): Result<GetListWalletResponseModel> {
        return safeApiCall { dataSource.getListWallets() }.map { it.mapToDomain() }
    }

    override suspend fun createTransaction(createTransactionRequestModel: CreateTransactionRequestModel): Result<CreateTransactionResponseModel> {
        return safeApiCall { dataSource.createTransaction(createTransactionRequestModel) }.map { it.mapToDomain() }
    }
}