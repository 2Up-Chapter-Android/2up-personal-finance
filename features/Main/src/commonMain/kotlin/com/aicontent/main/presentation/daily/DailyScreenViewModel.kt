package com.aicontent.main.presentation.daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategoryExpenses
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateTransactionById
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyScreenViewModel : ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseGetAllCategoryExpenses: UseCaseGetAllCategoryExpenses by inject()
    private val useCaseGetAllCategoryIncome: UseCaseGetAllCategoryIncome by inject()
    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseDeleteTransactionById: UseCaseDeleteTransactionById by inject()
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

    fun updateTransaction(transaction: TransactionLocalModel) {
        useCaseUpdateTransaction.updateTransaction(
            transaction = transaction,
            loadTransaction = loadTransaction()
        )
    }

    // Function to update transactionUiState with a selected transaction
    fun updateTransactionUiState(transaction: TransactionLocalModel) {
        _transactionUiState.value = transactionUiState.value.copy(
            id = transaction.transaction_id!!,
            date = transaction.transaction_created,
            category = transaction.transaction_category,
            account = transaction.transaction_account,
            note = transaction.transaction_note,
            description = transaction.transaction_description,
            income = transaction.transaction_income,
            expenses = transaction.transaction_expenses,
            transfer = transaction.transaction_transfer,
            accountFrom = transaction.transaction_accountFrom,
            accountTo = transaction.transaction_accountTo,
            selectIndex = transaction.transaction_selectIndex
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
//            showSaveButton = true
        )
    }

    fun onCategoryChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            category = text,
//            showSaveButton = true
        )
    }

    fun onAccountChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            account = text,
//            showSaveButton = true
        )
    }

    fun onAccountFromChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            accountFrom = text,
//            showSaveButton = true
        )
    }

    fun onAccountToChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            accountTo = text,
//            showSaveButton = true
        )
    }

    fun onNoteChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            note = text,
//            showSaveButton = true
        )
    }

    fun onDescriptionChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            description = text,
//            showSaveButton = true
        )
    }

    fun onIncomeChange(text: Long) {
        _transactionUiState.value = transactionUiState.value.copy(
            income = text,
//            showSaveButton = true
        )
    }

    fun onExpensesChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            expenses = text.toLong(),
//            showSaveButton = true
        )
    }

    fun onTransferChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            transfer = text.toLong(),
//            showSaveButton = true
        )
    }

    fun openCloseDatePicker(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenDatePicker = isOpen,
//            showSaveButton = isOpen
        )
    }

    fun openCloseChooseWallet(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseWallet = isOpen,
//            showSaveButton = isOpen
        )
    }

    fun openCloseChooseCategory(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseCategory = isOpen,
//            showSaveButton = isOpen
        )
    }

    fun openCloseChooseCategoryAccountTo(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountTo = isOpen,
//            showSaveButton = isOpen
        )
    }

    fun openCloseChooseCategoryAccountFrom(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountFrom = isOpen,
//            showSaveButton = isOpen
        )
    }
}