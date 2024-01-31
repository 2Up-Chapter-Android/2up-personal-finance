package com.aicontent.main.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByYear
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseSearchTransactionByNote
import com.twoup.personalfinance.domain.usecase.transaction.GetListTransactionUseCase
import com.twoup.personalfinance.utils.DateTimeUtil
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreenViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseGetListTransaction: GetListTransactionUseCase by inject()
    private val useCaseFilterTransactionByMonth: UseCaseFilterTransactionByMonth by inject()
    private val useCaseFilterTransactionByYear: UseCaseFilterTransactionByYear by inject()
    private val useCaseSearchTransactionByNote: UseCaseSearchTransactionByNote by inject()
    private val _searchResults = useCaseSearchTransactionByNote.searchResults
    private val _searchResultsFlow = MutableStateFlow(_searchResults)

    private val _getListTransactionState =
        MutableStateFlow<Resource<GetAllTransactionsResponseModel>>(Resource.loading())

    private val _selectedTransaction = MutableStateFlow<TransactionLocalModel?>(null)
//    val selectedTransaction: StateFlow<TransactionLocalModel?> get() = _selectedTransaction.asStateFlow()

    val getListTransactionState = _getListTransactionState.asStateFlow()

    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
    val transactionByMonth: StateFlow<List<TransactionLocalModel>> get() = useCaseFilterTransactionByMonth.listTransactionState.asStateFlow()
    val transactionByYear: StateFlow<List<TransactionLocalModel>> get() = useCaseFilterTransactionByYear.listTransactionState.asStateFlow()
//    val transactionByNote: StateFlow<List<TransactionLocalModel>> get() = useCaseSearchTransactionByNote.listTransactionState.asStateFlow()

    // Encapsulate current month and year
    var currentMonthYear =
        mutableStateOf(MonthYear(DateTimeUtil.now().monthNumber, DateTimeUtil.now().year))


    val searchResults: MutableStateFlow<StateFlow<List<TransactionLocalModel>>> = _searchResultsFlow

    var openDiaLog: MutableState<Boolean> = mutableStateOf(false)

    init {
        loadTransaction()
        getListTransaction()
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
        filterTransactionByYear(currentMonthYear.value.year)
        generateCalendarData(currentMonthYear)
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

    fun filterTransactionByYear(year: Int) {
        useCaseFilterTransactionByYear.filterTransactionByYear(year.toLong())
    }

//    fun searchTransactionByNote(note: String, description: String) {
//        useCaseSearchTransactionByNote.searchTransaction(note, description)
//    }

    fun searchTransaction(query: String) {
        useCaseSearchTransactionByNote.searchTransaction(query)
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
//        filterTransactionByYear(currentMonthYear.value.year)
    }

    private fun adjustYear(offset: Int) {
        val currentYear = currentMonthYear.value.year

        // Calculate the target year once
        val targetYear = currentYear + offset

        // Update the currentMonthYear
        currentMonthYear.value = currentMonthYear.value.copy(year = targetYear)

        // Filter transactions for the target year
        filterTransactionByYear(targetYear)
    }

    fun openCloseDatePicker(isOpen: Boolean) {
        openDiaLog.value = isOpen
    }

    fun onDateMonthChange(text: LocalDateTime) {
        currentMonthYear.value = MonthYear(text.monthNumber, text.year)
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
        filterTransactionByYear(currentMonthYear.value.year)

    }

//    fun onDateYearChange(text: LocalDateTime) {
//        filterTransactionByYear(text.year)
//    }

    fun decrementYear() {
        adjustYear(-1)
    }

    fun incrementYear() {
        adjustYear(1)
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
//        filterTransactionByYear(currentMonthYear.value.year)
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
        return transactions.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
    }

//    fun calculateColorText(transaction: TransactionLocalModel): Color {
//        return when {
//            transaction.transactionIncome - transaction.transactionExpenses > 0 -> Color.Blue
//            transaction.transactionExpenses - transaction.transactionIncome > 0 -> Color.Red
//            else -> Color.Black
//        }
//    }

//    fun calculateIncomeOrExpenses(transaction: TransactionLocalModel): Long {
//        return when {
//            transaction.transactionIncome - transaction.transactionExpenses > 0 -> transaction.transactionIncome
//            transaction.transactionExpenses - transaction.transactionIncome > 0 -> transaction.transactionExpenses
//            else -> 0
//        }
//    }

    private var _calendarDays =
        MutableStateFlow<List<LocalDateTime>>(emptyList()) // Ensure _calendarDays is properly initialized

//    var calendarDays: StateFlow<List<LocalDateTime>> = _calendarDays.asStateFlow()

    fun generateCalendarData(selectedDate: MutableState<MonthYear>): StateFlow<List<LocalDateTime>> {

        val allDaysInMonth =
            DateTimeUtil.getAllDaysInMonth(selectedDate.value.year, selectedDate.value.month)
        val allDayInMonthBefore =
            DateTimeUtil.getAllDaysInMonthBefore(selectedDate.value.year, selectedDate.value.month)
        val allDayInMonthAfter =
            DateTimeUtil.getAllDaysInMonthAfter(selectedDate.value.year, selectedDate.value.month)

        val (firstDayInMonth) = DateTimeUtil.getFirstAndLastDayOfMonth(
            selectedDate.value.year,
            selectedDate.value.month
        )

        val remainingDaysInFirstRow = firstDayInMonth.dayOfWeek.ordinal

        val daysBeforeFirstRow = allDayInMonthBefore.takeLast(remainingDaysInFirstRow)

        val remainingDaysInLastRow = (7 - ((allDaysInMonth.size + daysBeforeFirstRow.size) % 7))

        val daysAfterLastRow = allDayInMonthAfter.take(remainingDaysInLastRow)

        val hmm = (daysBeforeFirstRow.size + allDaysInMonth.size + daysAfterLastRow.size)
        val dayList =
            if (hmm == 35 ) {
                daysBeforeFirstRow + allDaysInMonth + allDayInMonthAfter.take(remainingDaysInLastRow + 35 - hmm + 7)
            } else {
                daysBeforeFirstRow + allDaysInMonth + daysAfterLastRow
            }

        // Ensure _calendarDays is not null before accessing its value
        _calendarDays?.value = dayList // NullPointerException occurs here if _calendarDays is null

        return _calendarDays
    }


    data class MonthYear(var month: Int, val year: Int)
}
