package com.twoup.personalfinance.remote.services.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.remote.dto.transaction.CreateTransactionResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface TransactionService {
    @POST("transactions")
    suspend fun createTransaction(@Body createTransactionRequest: CreateTransactionRequestModel): Result<CreateTransactionResponse>

}