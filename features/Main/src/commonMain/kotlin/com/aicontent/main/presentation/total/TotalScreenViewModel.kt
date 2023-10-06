package com.aicontent.main.presentation.total

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TotalScreenViewModel : ScreenModel, KoinComponent {
//    private val useCaseFilterTransactionByMonth : UseCaseFilterTransactionByMonth by inject()
//
//    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
//    private val useCaseUpdateAccountById: UseCaseUpdateAccountById by inject()
//    private val useCaseInsertAccount: UseCaseInsertAccount by inject()
//    private val useCaseDeleteAccountById: UseCaseDeleteAccountById by inject()
//    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
//    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
//    val transactions : StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
//    init {
//        useCaseGetAllTransaction.getAllTransaction()
//    }
//    fun filterTransactionByMonth(listTransaction : List<TransactionLocalModel>){
//        useCaseFilterTransactionByMonth.filterTransactionByMonth(listTransaction)
//    }
}