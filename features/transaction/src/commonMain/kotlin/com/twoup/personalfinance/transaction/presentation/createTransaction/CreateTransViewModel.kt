package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryExpenses
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetTransactionById
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CreateTransViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseGetAllCategoryExpenses: UseCaseGetAllCategoryExpenses by inject()
    private val useCaseGetAllCategoryIncome: UseCaseGetAllCategoryIncome by inject()
    private val useCaseInsertTransaction: UseCaseInsertTransaction by inject()
    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()
    private val useCaseUpdateAccountById: UseCaseUpdateAccountById by inject()
    private val useCaseGetTransactionById: UseCaseGetTransactionById by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categoryExpenses: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategoryExpenses.categoryExpenseState.asStateFlow()
    val categoryIncome: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategoryIncome.categoryIncomeState.asStateFlow()
    val transactionById:StateFlow<TransactionLocalModel> get() = useCaseGetTransactionById.transactionState.asStateFlow()

    private val getListWalletsUseCase: GetListWalletsUseCase by inject()
    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()
    private val _getListWalletState =
        MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
    val getListWalletState = _getListWalletState.asStateFlow()

    init {
        loadTransaction()
        loadAccount()
        useCaseGetAllCategoryExpenses.getAllCategoryExpenses()
        useCaseGetAllCategoryIncome.getAllCategoryIncome()
    }

    fun loadTransaction() {
        useCaseGetAllTransaction.getAllTransaction()
    }

    private fun loadAccount() {
        useCaseGetAllAccount.getAllAccount()
    }
    fun getTransactionById(id: Long) {
        try {
            useCaseGetTransactionById.getTransactionById(id)
        } catch (e: Exception) {
            // Handle the error, e.g., show an error message to the user.
        }
    }
    fun updateCreateTransUiState(transaction: TransactionLocalModel) {
        val currentUiState = createTransUiState.value
        if (currentUiState.id != transaction.transactionId) {
            _createTransUiState.value = currentUiState.copy(
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
                selectIndex = transaction.transactionSelectIndex,
                month = transaction.transactionMonth,
                year = transaction.transactionYear
            )
        }
    }



    fun insertTransaction(transaction: TransactionLocalModel) {
        useCaseInsertTransaction.insertTransaction(transaction, loadTransaction())
    }

    fun updateAccountFrom(account: AccountLocalModel) {
        useCaseUpdateAccountById.updateAccount(
            account, useCaseGetAllAccount.getAllAccount()
        )
    }

    fun updateAccountTo(account: AccountLocalModel) {
        useCaseUpdateAccountById.updateAccount(
            account, useCaseGetAllAccount.getAllAccount()
        )
    }

     @OptIn(DelicateCoroutinesApi::class)
     fun getListWallets() {
        GlobalScope.launch {
            getListWalletsUseCase().collectLatest {
                _getListWalletState.emit(it)
            }
        }
    }

    fun onDateChange(text: LocalDateTime) {
        _createTransUiState.value = createTransUiState.value.copy(
            date = text
        )
    }

    fun onIncomeChange(text: String) {
        try {
            val income = if (text.isBlank()) 0L else text.toLong()
            _createTransUiState.value = createTransUiState.value.copy(income = income)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
            // For example, you can set a validation error state in your UI
        }
    }

    fun onExpensesChange(text: String) {
        try {
            val expenses = if (text.isBlank()) 0L else text.toLong()
            _createTransUiState.value = createTransUiState.value.copy(expenses = expenses)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
        }
    }

    fun onTransferChange(text: String) {
        try {
            val transferBalance = if (text.isBlank()) 0L else text.toLong()
            _createTransUiState.value =
                createTransUiState.value.copy(transfer = transferBalance)
        } catch (e: NumberFormatException) {
            // Handle the case where text is not a valid long (non-numeric input)
        }
    }

    fun onCategoryChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            category = text
        )
    }

    fun onAccountChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            account = text
        )
    }

    fun onAccountFromChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            accountFrom = text
        )
    }

    fun onAccountToChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            accountTo = text
        )
    }

    fun onNoteChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            note = text
        )
    }

    fun onDescriptionChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            description = text
        )
    }

    fun openCloseDatePicker(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenDatePicker = isOpen
        )
    }

    fun openCloseChooseWallet(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseWallet = isOpen
        )
    }

    fun openCloseChooseCategory(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseCategory = isOpen
        )
    }

    fun openCloseChooseCategoryAccountTo(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseAccountTo = isOpen
        )
    }

    fun openCloseChooseCategoryAccountFrom(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseAccountFrom = isOpen
        )
    }
}