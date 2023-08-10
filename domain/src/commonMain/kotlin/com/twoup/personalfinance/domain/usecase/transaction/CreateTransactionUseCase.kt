package com.twoup.personalfinance.domain.usecase.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository

class CreateTransactionUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(createTransactionRequestModel: CreateTransactionRequestModel) = repository.createTransaction(createTransactionRequestModel)
}