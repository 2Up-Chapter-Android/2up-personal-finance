package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.local.IDatabase
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource
import com.twoup.personalfinance.remote.util.networkBoundResource
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TransactionRepositoryImpl(
    private val dataSource: TransactionDataSource,
    private val database: IDatabase
) : TransactionRepository {
    override suspend fun getListWallets(): Flow<Resource<GetListWalletResponseModel>> {
        return networkBoundResource(
            query = {
                flowOf(GetListWalletResponseModel(data = database.getAllLWallet()))
            },
            fetch = {
                dataSource.getListWallets().map { it.mapToDomain()}
            },
            saveFetchResult = {fetchResult ->
                fetchResult.data?.data?.apply {
                    database.clearAllWallets()
                    this.forEach{
                    database.insertWallet(
                        id = it.id,
                        name = it.name,
                        amount = it.amount,
                        description = it.description,
                        walletGroup = it.walletGroup,
                    )
                }
                }

            }
        )
    }

    override suspend fun getListTransactions(): Flow<Resource<GetAllTransactionsResponseModel>> {
        return networkBoundResource(
            query = {
                flowOf(GetAllTransactionsResponseModel(data = database.getAllTransaction()))
            },
            fetch = {
                dataSource.getListTransaction().map { it.mapToDomain()}
            },
            saveFetchResult = { fetchResult ->
                fetchResult.data?.data?.apply {
                    database.clearAllTransactions()
                    this.forEach {
                        database.insertTransaction(
                            id = it.id,
                            amount = it.amount,
                            categoryId = it.categoryId,
                            createdAt = it.createdAt,
                            description = it.description,
                            note = it.note,
                            type = it.type,
                            updatedAt = it.updatedAt,
                            walletId = it.walletId
                        )
                    }
                }
            }
        )
    }
}