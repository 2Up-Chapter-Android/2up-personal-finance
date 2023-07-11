package com.twoup.personalfinance.domain.usecase.transaction

import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository

class GetListWalletsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke() = repository.getListWallets()
}