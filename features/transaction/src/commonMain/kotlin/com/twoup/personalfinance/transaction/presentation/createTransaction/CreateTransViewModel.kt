package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategory
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.utils.data.Resource
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
    private val useCaseGetAllCategory: UseCaseGetAllCategory by inject()
    private val useCaseInsertTransaction: UseCaseInsertTransaction by inject()
    private val useCaseGetAllTransaction: UseCaseGetAllAccount by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categorys: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategory.categoryState.asStateFlow()

    init {
        loadTransaction()
        useCaseGetAllAccount.getAllAccount()
        useCaseGetAllCategory.getAllCategory()
        getListWallets()

    }

    fun loadTransaction() {
        useCaseGetAllTransaction.getAllAccount()

    }

    private val getListWalletsUseCase: GetListWalletsUseCase by inject()

    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    private val _getListWalletState =
        MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
    val getListWalletState = _getListWalletState.asStateFlow()

    fun insertTransaction(transaction: TransactionLocalModel) {
        useCaseInsertTransaction.insertTransaction(transaction)
    }

    private fun getListWallets() {
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

    fun onAmountChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            amount = text.toDouble()
        )
    }

    fun onCategoryChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            category = text
        )
    }

//    fun onAccountChange(wallet: Wallet) {
//        _createTransUiState.value = createTransUiState.value.copy(
//            account = wallet
//        )
//    }

    fun onAccountChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            account = text
        )
    }


    fun onNoteChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            note = text
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

    fun openCloseChooseAmount(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseAmount = isOpen
        )
    }
}