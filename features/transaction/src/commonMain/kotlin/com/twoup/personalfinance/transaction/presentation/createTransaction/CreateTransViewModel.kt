package com.twoup.personalfinance.transaction.presentation.createTransaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategory
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CreateTransViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseGetAllCategory: UseCaseGetAllCategory by inject()
    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val categorys: StateFlow<List<CategoryLocalModel>> get() = useCaseGetAllCategory.categoryState.asStateFlow()

    init {
        useCaseGetAllAccount.getAllAccount()
        useCaseGetAllCategory.getAllCategory()
    }

    private val getListWalletsUseCase: GetListWalletsUseCase by inject()

    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    private val _getListWalletState =
        MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
    val getListWalletState = _getListWalletState.asStateFlow()

    private val _showBottomSheetAccount = mutableStateOf(false)
    val showBottomSheetAccount: State<Boolean> = _showBottomSheetAccount

    private val _showBottomSheetCategory = mutableStateOf(false)
    val showBottomSheetCategory: State<Boolean> = _showBottomSheetCategory

    private val _showBottomSheetAmount = mutableStateOf(false)
    val showBottomSheetAmount: State<Boolean> = _showBottomSheetAmount

    private fun toggleBottomSheetState(
        targetSheet: MutableState<Boolean>,
        otherSheets: List<MutableState<Boolean>>
    ) {
        targetSheet.value = !targetSheet.value
        if (targetSheet.value) {
            otherSheets.forEach { it.value = false }
        }
    }

    fun changeShowBottomSheetAccount() {
        toggleBottomSheetState(
            _showBottomSheetAccount,
            listOf(_showBottomSheetCategory, _showBottomSheetAmount)
        )
    }

    fun changeShowBottomSheetCategory() {
        toggleBottomSheetState(
            _showBottomSheetCategory,
            listOf(_showBottomSheetAccount, _showBottomSheetAmount)
        )
    }

    fun changeShowBottomSheetAmount() {
        toggleBottomSheetState(
            _showBottomSheetAmount,
            listOf(_showBottomSheetAccount, _showBottomSheetCategory)
        )
    }

    init {
        getListWallets()
    }

    fun changeAccount(textButton: String) {
        createTransUiState.value.textAccount = textButton
    }
    fun changeCategory(textButton: String) {
        createTransUiState.value.category = textButton
    }

    private fun getListWallets() {
        GlobalScope.launch {
            getListWalletsUseCase().collectLatest {
                _getListWalletState.emit(it)
            }
        }
    }

    fun onDateChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            date = text
        )
    }

    fun onAmountChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            amount = text
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

    fun openCloseChooseWallet(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseWallet = isOpen
        )
    }
    fun openCloseChooseCategory(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseCategory = isOpen
        )
    } fun openCloseChooseAmount(isOpen: Boolean) {
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseAmount = isOpen
        )
    }
}