package com.aicontent.main.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.transaction.GetListTransactionUseCase
import com.twoup.personalfinance.utils.DateTimeUtil
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreenViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseGetListTransaction: GetListTransactionUseCase by inject()
    private val useCaseFilterTransactionByMonth: UseCaseFilterTransactionByMonth by inject()
    private val _getListTransactionState =
        MutableStateFlow<Resource<GetAllTransactionsResponseModel>>(Resource.loading())

    var currentMonth: Int = DateTimeUtil.now().monthNumber
    var currentYear: Int = DateTimeUtil.now().year

    val getListTransactionState = _getListTransactionState.asStateFlow()
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
    val transactionByMonth: StateFlow<List<TransactionLocalModel>> get() = useCaseFilterTransactionByMonth.listTransactionState.asStateFlow()

    init {
        loadNotes()
        getListTransaction()
    }

    fun loadNotes() {
        useCaseGetAllTransaction.getAllTransaction()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getListTransaction() {
        GlobalScope.launch {
            useCaseGetListTransaction.invoke().collect {
                _getListTransactionState.emit(it)
            }
        }
    }

    fun filterTransactionByMonth(currentMonth: Number, currentYear: Number) {
        useCaseFilterTransactionByMonth.filterTransactionByMonth(currentMonth, currentYear)
    }

    fun getCurrentMonthAndYear(): Pair<Int, Int> {
        return Pair(currentMonth, currentYear)
    }

    // Function to decrement the month and year
    fun decrementMonth() {
        currentMonth--
        if (currentMonth < 1) {
            currentMonth = 12 // Wrap around to December
            currentYear--
        }
    }

    // Function to increment the month and year
    fun incrementMonth() {
        currentMonth++
        if (currentMonth > 12) {
            currentMonth = 1 // Wrap around to January
            currentYear++
        }
    }
}

