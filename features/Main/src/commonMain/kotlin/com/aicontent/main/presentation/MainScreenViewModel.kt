package com.aicontent.main.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
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

    val getListTransactionState = _getListTransactionState.asStateFlow()
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
    val transactionByMonth: StateFlow<List<TransactionLocalModel>> get() = useCaseFilterTransactionByMonth.listTransactionState.asStateFlow()

    // Encapsulate current month and year
    val currentMonthYear = mutableStateOf(MonthYear(DateTimeUtil.now().monthNumber, DateTimeUtil.now().year))

    init {
        loadTransaction()
        getListTransaction()
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
    }

    fun loadTransaction() {
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

    fun filterTransactionByMonth(month: Int, year: Int) {
        useCaseFilterTransactionByMonth.filterTransactionByMonth(month.toLong(), year.toLong())
    }

    fun decrementMonth() {
        val newMonth = currentMonthYear.value.month - 1
        val newYear = currentMonthYear.value.year

        if (newMonth >= 1) {
            currentMonthYear.value = currentMonthYear.value.copy(month = newMonth)
        } else {
            // If the month is less than 1, wrap around to December of the previous year
            currentMonthYear.value = currentMonthYear.value.copy(month = 12, year = newYear - 1)
        }
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
    }

    fun incrementMonth() {
        val newMonth = currentMonthYear.value.month + 1
        val newYear = currentMonthYear.value.year

        if (newMonth <= 12) {
            currentMonthYear.value = currentMonthYear.value.copy(month = newMonth)
        } else {
            // If the month is greater than 12, wrap around to January of the next year
            currentMonthYear.value = currentMonthYear.value.copy(month = 1, year = newYear + 1)
        }
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
    }
    fun getAbbreviatedMonth(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "Invalid Month"
        }
    }
    fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transaction_income > 0 }.sumOf { it.transaction_income }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transaction_expenses > 0 }.sumOf { it.transaction_expenses }
    }
}

data class MonthYear(val month: Int, val year: Int)
