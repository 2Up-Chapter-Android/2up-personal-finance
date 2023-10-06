package com.aicontent.main.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BudgetBoxViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    init {
        loadNotes()
    }
    private fun loadNotes() {
        useCaseGetAllTransaction.getAllTransaction()
    }
}
