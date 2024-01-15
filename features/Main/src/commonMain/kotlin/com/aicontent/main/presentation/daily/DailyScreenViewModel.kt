package com.aicontent.main.presentation.daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseDeleteTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryExpenses
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseUpdateTransactionById
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyScreenViewModel : ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseGetAllCategoryExpenses: UseCaseGetAllCategoryExpenses by inject()
    private val useCaseGetAllCategoryIncome: UseCaseGetAllCategoryIncome by inject()
    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseDeleteTransactionById: UseCaseDeleteTransactionById by inject()
    private val useCaseFilterTransactionByMonth: UseCaseFilterTransactionByMonth by inject()
    private val useCaseInsertTransaction: UseCaseInsertTransaction by inject()
    private val useCaseUpdateAccountById: UseCaseUpdateAccountById by inject()
    private val useCaseUpdateTransaction: UseCaseUpdateTransactionById by inject()

    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categoryExpenses: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategoryExpenses.categoryExpenseState.asStateFlow()
    val categoryIncome: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategoryIncome.categoryIncomeState.asStateFlow()

    init {
        loadTransaction()
        loadAccount()
        useCaseGetAllCategoryExpenses.getAllCategoryExpenses()
        useCaseGetAllCategoryIncome.getAllCategoryIncome()
    }

    private fun loadAccount() {
        useCaseGetAllAccount.getAllAccount()
    }

    fun loadTransaction() {
        useCaseGetAllTransaction.getAllTransaction()
    }

    fun filterTransactionByMonth(month: Int, year: Int) {
        useCaseFilterTransactionByMonth.filterTransactionByMonth(month.toLong(), year.toLong())
    }

    fun updateTransaction(transaction: TransactionLocalModel) {
        useCaseUpdateTransaction.updateTransaction(
            transaction = transaction,
            loadTransaction = loadTransaction()
        )
    }

    // Function to update transactionUiState with a selected transaction
    fun updateTransactionUiState(transaction: TransactionLocalModel) {
        _transactionUiState.value = transactionUiState.value.copy(
            id = transaction.transactionId,
            date = transaction.transactionCreated,
            category = transaction.transactionCategory,
            account = transaction.transactionAccount,
            note = transaction.transactionNote,
            description = transaction.transactionDescription,
            income = transaction.transactionIncome,
            expenses = transaction.transactionExpenses,
            transfer = transaction.transactionTransfer,
            accountFrom = transaction.transactionAccountFrom,
            accountTo = transaction.transactionAccountTo,
            selectIndex = transaction.transactionSelectIndex
        )
    }

    fun updateShowSaveButton() {
        _transactionUiState.value = transactionUiState.value.copy(
            showSaveButton = true
        )
    }

    fun deleteTransactionById(id: Long) {
        useCaseDeleteTransactionById.deleteTransactionById(id, loadTransaction())
    }

    private val _transactionUiState = MutableStateFlow(TransactionUiState())
    val transactionUiState = _transactionUiState.asStateFlow()

    // insert get list transaction
    fun onDateChange(text: LocalDateTime) {
        _transactionUiState.value = transactionUiState.value.copy(
            date = text,
        )
    }

    fun onCategoryChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            category = text,
        )
    }

    fun onAccountChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            account = text,
        )
    }

    fun onAccountFromChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            accountFrom = text,
        )
    }

    fun onAccountToChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            accountTo = text,
        )
    }

    fun onNoteChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            note = text,
        )
    }

    fun onDescriptionChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            description = text,
        )
    }

    fun onIncomeChange(text: String) {
        try {
            val income = if (text.isBlank()) 0L else text.toLong()
            _transactionUiState.value = _transactionUiState.value.copy(income = income)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
            // For example, you can set a validation error state in your UI
        }
    }

    fun onExpensesChange(text: String) {
        try {
            val expenses = if (text.isBlank()) 0L else text.toLong()
            _transactionUiState.value = _transactionUiState.value.copy(expenses = expenses)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
        }
    }

    fun onTransferChange(text: String) {
        try {
            val transferBalance = if (text.isBlank()) 0L else text.toLong()
            _transactionUiState.value = _transactionUiState.value.copy(transfer = transferBalance)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
        }
    }

    fun openCloseDatePicker(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenDatePicker = isOpen,
        )
    }

    fun openCloseChooseWallet(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseWallet = isOpen,
        )
    }

    fun openCloseChooseCategory(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseCategory = isOpen,
        )
    }

    fun openCloseChooseCategoryAccountTo(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountTo = isOpen,
        )
    }

    fun openCloseChooseCategoryAccountFrom(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountFrom = isOpen,
        )
    }

    fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
    }

    fun calculateColorText(transaction: TransactionLocalModel): Color {
        return when {
            transaction.transactionExpenses - transaction.transactionExpenses > 0 -> Color.Blue
            transaction.transactionIncome - transaction.transactionIncome > 0 -> Color.Red
            else -> Color.Black
        }
    }

    fun calculateIncomeOrExpenses(transaction: TransactionLocalModel): Long {
        return when {
            transaction.transactionIncome - transaction.transactionExpenses > 0 -> transaction.transactionIncome
            transaction.transactionExpenses - transaction.transactionIncome > 0 -> transaction.transactionExpenses
            else -> 0
        }
    }

}