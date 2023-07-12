package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.transaction.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource
import com.twoup.personalfinance.remote.util.safeApiCall
import com.twoup.personalfinance.remote.util.toResource

class TransactionRepositoryImpl(private val dataSource: TransactionDataSource) : TransactionRepository {
    override suspend fun getListWallets(): Result<GetListWalletResponseModel> {
        return safeApiCall { dataSource.getListWallets() }.map { it.mapToDomain() }
    }
}