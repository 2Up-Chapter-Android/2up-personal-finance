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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyScreenViewModel : ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
    private val useCaseGetAllCategoryExpenses: UseCaseGetAllCategoryExpenses by inject()
    private val useCaseGetAllCategoryIncome: UseCaseGetAllCategoryIncome by inject()
    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val  useCaseDeleteTransactionById : UseCaseDeleteTransactionById by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categorys: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategoryExpenses.categoryExpenseState.asStateFlow()

    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    init {
        loadTransaction()
    }
    fun loadTransaction() {
        useCaseGetAllTransaction.getAllTransaction()
    }
    fun deleteTransactionById(id: Long ){
        useCaseDeleteTransactionById.deleteTransactionById(id,loadTransaction())
    }

    private val _transactionUiState = MutableStateFlow(TransUiState())
    val transactionUiState = _transactionUiState.asStateFlow()

    // insert get list transaction
    fun onDateChange(text: LocalDateTime) {
        _transactionUiState.value = transactionUiState.value.copy(
            date = text
        )
    }

    fun onAmountChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            amount = text,
        )
    }

    fun onCategoryChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            category = text
        )
    }

    fun onAccountChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            account = text
        )
    }
    fun onNoteChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            note = text
        )
    }
    fun openCloseDatePicker(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenDatePicker = isOpen
        )
    }
    fun openCloseChooseWallet(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseWallet = isOpen
        )
    }
    fun openCloseChooseCategory(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseCategory = isOpen
        )
    }
    fun openCloseChooseAmount(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAmount = isOpen
        )
    }
    fun onIncomeChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            income = text.toLong()
        )
    }
    fun onExpensesChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            expenses = text.toLong()
        )
    }
    fun onTransferChange(text: String) {
        _transactionUiState.value = transactionUiState.value.copy(
            transfer = text.toLong()
        )
    }
    fun openCloseChooseCategoryAccountTo(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountTo = isOpen
        )
    }
    fun openCloseChooseCategoryAccountFrom(isOpen: Boolean) {
        _transactionUiState.value = transactionUiState.value.copy(
            isOpenChooseAccountFrom = isOpen
        )
    }
}