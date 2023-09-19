package com.aicontent.main.presentation.daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategory
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyScreenViewModel : ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
    private val useCaseGetAllCategory: UseCaseGetAllCategory by inject()
    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categorys: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategory.categoryState.asStateFlow()

    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    init {
        loadNotes()
    }
    fun loadNotes() {
        useCaseGetAllTransaction.getAllTransaction()
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
}