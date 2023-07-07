package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource

class TransactionRepositoryImpl(private val dataSource: TransactionDataSource) : TransactionRepository {

}