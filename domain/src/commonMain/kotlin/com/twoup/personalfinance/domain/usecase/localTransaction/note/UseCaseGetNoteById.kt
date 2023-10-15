package com.twoup.personalfinance.domain.usecase.localTransaction.note

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetNoteById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun getNoteById(id: Long) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.getNoteById(id)
            }
        }
    }
}