package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.local.IDatabase
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource
import com.twoup.personalfinance.remote.util.safeApiCall
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.map

class TransactionRepositoryImpl(
    private val dataSource: TransactionDataSource,
    private val database: IDatabase
) : TransactionRepository {
    override suspend fun getListWallets(): Resource<GetListWalletResponseModel> {
        return safeApiCall { dataSource.getListWallets() }.map { it.mapToDomain() }
    }
}