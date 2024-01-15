package com.aicontent.accounts.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseUpdateAccountById
import com.twoup.personalfinance.utils.DateTimeUtil
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AccountListViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseUpdateAccountById: UseCaseUpdateAccountById by inject()
    private val useCaseInsertAccount: UseCaseInsertAccount by inject()
    private val useCaseDeleteAccountById: UseCaseDeleteAccountById by inject()
    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseFilterTransactionByMonth: UseCaseFilterTransactionByMonth by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val transactions: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
    val currentMonthYear =
        mutableStateOf(MonthYear(DateTimeUtil.now().monthNumber, DateTimeUtil.now().year))
    val transactionByMonth: StateFlow<List<TransactionLocalModel>> get() = useCaseFilterTransactionByMonth.listTransactionState.asStateFlow()

    init {
        loadAccount()
        filterTransactionByMonth(currentMonthYear.value.month, currentMonthYear.value.year)
    }

    fun loadAccount() {
        useCaseGetAllAccount.getAllAccount()
        useCaseGetAllTransaction.getAllTransaction()
    }

    fun updateAccount(account: AccountLocalModel) {
        useCaseUpdateAccountById.updateAccount(account, loadAccount())
    }

    fun deleteAccount(id: Long) {
        useCaseDeleteAccountById.deleteAccountById(id, loadAccount())
    }

    fun insertAccount(account: AccountLocalModel) {
        useCaseInsertAccount.insertAccount(account)
    }

    // Function to get filtered account transactions
    fun getAccountTransactions(
        allTransactions: List<TransactionLocalModel>,
        account: AccountLocalModel
    ): List<TransactionLocalModel> {
        return allTransactions.filter {
            it.transactionAccount == account.account_name
                    || it.transactionAccountFrom == account.account_name
                    || it.transactionAccountTo == account.account_name
//            it.transaction_account == account.account_name
//                    || it.transaction_accountFrom == account.account_name
//                    || it.transaction_accountTo == account.account_name
        }
    }

    // Create StateFlow properties to hold the data
    private val _income = MutableStateFlow(0L)
    val income: StateFlow<Long> = _income

    private val _expenses = MutableStateFlow(0L)
    val expenses: StateFlow<Long> = _expenses

    private val _transferFrom = MutableStateFlow(0L)
    val transferFrom: StateFlow<Long> = _transferFrom

    private val _transferTo = MutableStateFlow(0L)
    val transferTo: StateFlow<Long> = _transferTo

    private val _total = MutableStateFlow(0L)
    val total: StateFlow<Long> = _total

    private val _totalAccountIncome = MutableStateFlow(0L)
    val totalAccountIncome: StateFlow<Long> = _totalAccountIncome

    private val _totalAccountExpenses = MutableStateFlow(0L)
    val totalAccountExpenses: StateFlow<Long> = _totalAccountExpenses

    private val _totalAccountTransferFrom = MutableStateFlow(0L)
    val totalAccountTransferFrom: StateFlow<Long> = _totalAccountTransferFrom

    private val _totalAccountTransferTo = MutableStateFlow(0L)
    val totalAccountTransferTo: StateFlow<Long> = _totalAccountTransferTo

    private val _colorText = MutableStateFlow(Color.Black)
    val colorText: StateFlow<Color> = _colorText

    private val _totalAccountValue = MutableStateFlow(0L)
    val totalAccountValue: StateFlow<Long> = _totalAccountValue

    // Add a function to update the data
    fun updateData(
        listTransaction: List<TransactionLocalModel>,
        allTransaction: List<TransactionLocalModel>,
        account: AccountLocalModel
    ) {
        val incomeValue = listTransaction.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
        val expensesValue = listTransaction.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
        val transferFromValue = listTransaction.filter { it.transactionAccountFrom == account.account_name }.sumOf { it.transactionTransfer }
        val transferToValue = listTransaction.filter { it.transactionAccountTo == account.account_name }.sumOf { it.transactionTransfer }
//        val totalValue = incomeValue - expensesValue - transferFromValue + transferToValue
        val totalAccountIncomeValue =
            allTransaction.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
        val totalAccountExpensesValue =
            allTransaction.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
        val totalAccountTransferFromValue =
            allTransaction.filter { it.transactionAccountFrom == account.account_name }
                .sumOf { it.transactionTransfer }
        val totalAccountTransferToValue =
            allTransaction.filter { it.transactionAccountTo == account.account_name }
                .sumOf { it.transactionTransfer }
//            listTransaction.filter { it.transaction_income > 0 }.sumOf { it.transaction_income }
//        val expensesValue =
//            listTransaction.filter { it.transaction_expenses > 0 }.sumOf { it.transaction_expenses }
//        val transferFromValue =
//            listTransaction.filter { it.transaction_accountFrom == account.account_name }
//                .sumOf { it.transaction_transfer }
//        val transferToValue =
//            listTransaction.filter { it.transaction_accountTo == account.account_name }
//                .sumOf { it.transaction_transfer }
        val totalValue = incomeValue - expensesValue - transferFromValue + transferToValue

//        val totalAccountIncomeValue =
//            allTransaction.filter { it.transaction_income > 0 }.sumOf { it.transaction_income }
//        val totalAccountExpensesValue =
//            allTransaction.filter { it.transaction_expenses > 0 }.sumOf { it.transaction_expenses }
//        val totalAccountTransferFromValue =
//            allTransaction.filter { it.transaction_accountFrom == account.account_name }
//                .sumOf { it.transaction_transfer }
//        val totalAccountTransferToValue =
//            allTransaction.filter { it.transaction_accountTo == account.account_name }
//                .sumOf { it.transaction_transfer }
        val totalAccountValue =
            totalAccountIncomeValue - totalAccountExpensesValue + totalAccountTransferToValue - totalAccountTransferFromValue

        _income.value = incomeValue
        _expenses.value = expensesValue
        _transferFrom.value = transferFromValue
        _transferTo.value = transferToValue
        _total.value = totalValue
        _totalAccountIncome.value = totalAccountIncomeValue
        _totalAccountExpenses.value = totalAccountExpensesValue
        _totalAccountTransferFrom.value = totalAccountTransferFromValue
        _totalAccountTransferTo.value = totalAccountTransferToValue
        _totalAccountValue.value = totalAccountValue

        _colorText.value = when {
            totalValue > 0 || totalAccountValue > 0 -> Color.Blue
            totalValue < 0 || totalAccountValue < 0 -> Color.Red
            else -> Color.Black
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

//    fun getAbbreviatedMonth(monthNumber: Int): String {
//        return when (monthNumber) {
//            1 -> "Jan"
//            2 -> "Feb"
//            3 -> "Mar"
//            4 -> "Apr"
//            5 -> "May"
//            6 -> "Jun"
//            7 -> "Jul"
//            8 -> "Aug"
//            9 -> "Sep"
//            10 -> "Oct"
//            11 -> "Nov"
//            12 -> "Dec"
//            else -> "Invalid Month"
//        }
//    }
    fun getDefaultTransaction(): TransactionLocalModel {
        return TransactionLocalModel(
            transactionAccount = "", // Provide default values
            transactionAccountFrom = "",
            transactionAccountTo = "",
            transactionCategory = "",
            transactionDescription = "",
            transactionExpenses = 0,
            transactionId = 0,
            transactionIncome = 0,
            transactionMonth = 0,
            transactionNote = "",
            transactionSelectIndex = 0,
            transactionTransfer = 0,
            transactionYear = 0,
            transactionCreated = DateTimeUtil.now()
        )
    }

//    fun getAbbreviatedMonth(monthNumber: Int): String {
//        return when (monthNumber) {
//            1 -> "Jan"
//            2 -> "Feb"
//            3 -> "Mar"
//            4 -> "Apr"
//            5 -> "May"
//            6 -> "Jun"
//            7 -> "Jul"
//            8 -> "Aug"
//            9 -> "Sep"
//            10 -> "Oct"
//            11 -> "Nov"
//            12 -> "Dec"
//            else -> "Invalid Month"
//        }
//    }
//    fun getDefaultTransaction(): TransactionLocalModel {
//        return TransactionLocalModel(
//            transaction_account = "", // Provide default values
//            transaction_accountFrom = "",
//            transaction_accountTo = "",
//            transaction_category = "",
//            transaction_description = "",
//            transaction_expenses = 0,
//            transaction_id = 0,
//            transaction_income = 0,
//            transaction_month = 0,
//            transaction_note = "",
//            transaction_selectIndex = 0,
//            transaction_transfer = 0,
//            transaction_year = 0,
//            transaction_created = DateTimeUtil.now()
//>>>>>>> fd594fb534333d1d134a6821078b606b76c8c827
//        )
//    }

    fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
    }

    // Function to calculate the balance
    fun calculateBalance(
        selectedTransactions: List<TransactionLocalModel>,
        allTransactions: List<TransactionLocalModel>,
        account: AccountLocalModel
    ): Long {
        // Initialize variables to store different types of amounts
        var totalIncome = 0L
        var totalExpense = 0L
        var totalTransferFrom = 0L
        var totalTransferTo = 0L

        // Iterate through selected transactions to calculate income and expenses
        for (transaction in selectedTransactions) {
            totalIncome += transaction.transactionIncome
            totalExpense += transaction.transactionExpenses
        }

        // Iterate through all transactions with positive transfers to calculate transferTo amount
        for (transaction in allTransactions.filter { it.transactionTransfer > 0 }) {
            if (transaction.transactionAccountTo == account.account_name) {
                totalTransferTo += transaction.transactionTransfer
            }
        }

        // Iterate through all transactions with positive transfers to calculate transferFrom amount
        for (transaction in allTransactions.filter { it.transactionTransfer > 0 }) {
            if (transaction.transactionAccountFrom == account.account_name) {
                totalTransferFrom += transaction.transactionTransfer
            }
        }

        // Logging for debugging purposes
        Napier.d("totalIncome = $totalIncome", tag = "total")
        Napier.d("totalExpense = $totalExpense", tag = "total")
        Napier.d("totalTransferFrom = $totalTransferFrom", tag = "total")
        Napier.d("totalTransferTo = $totalTransferTo", tag = "total")

        // Return the calculated balance
        return totalIncome - totalExpense - totalTransferFrom + totalTransferTo
    }

//        return transactions.filter { it.transaction_income > 0 }.sumOf { it.transaction_income }
//    }

//    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
//        return transactions.filter { it.transaction_expenses > 0 }.sumOf { it.transaction_expenses }
//    }
}

data class MonthYear(val month: Int, val year: Int)