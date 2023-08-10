package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionResponseModel
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.transaction.CreateTransactionUseCase
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CreateTransViewModel : ScreenModel, KoinComponent {
    private val getListWalletsUseCase: GetListWalletsUseCase by inject()
    private val createTransUseCase: CreateTransactionUseCase by inject()

    private val _createTransState =
        MutableStateFlow<Resource<CreateTransactionResponseModel>>(Resource.loading())
    val createTransState = _createTransState.asStateFlow()

    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    private val _getListWalletState =
        MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
    val getListWalletState = _getListWalletState.asStateFlow()

    init {
        getListWallets()
    }

    private fun getListWallets() {
        GlobalScope.launch {
            getListWalletsUseCase().collectLatest {
                _getListWalletState.emit(it)
            }
        }
    }

    fun createTransaction(type: String) {

        _createTransUiState.value = createTransUiState.value.copy(isLoading = true)

        coroutineScope.launch {
            delay(200)
            val now = Clock.System.now()
            val milliseconds = now.toEpochMilliseconds()
            val createTransReponse = createTransUiState.value.account?.id?.let {
                CreateTransactionRequestModel(
                    amount = createTransUiState.value.amount,
                    categoryId = createTransUiState.value.category,
                    createdAt = milliseconds,
                    description = "88fbFFFF",
                    note = createTransUiState.value.note,
                    type = type,
                    walletId = it,
                )
            }?.let {
                createTransUseCase(
                    it
                )
            }
            _createTransUiState.value = createTransUiState.value.copy(isLoading = false)
            createTransReponse?.let { _createTransState.tryEmit(it) }

        }
    }

    fun onTypeChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            typeTrans = text
        )
    }

    fun onDateChange(text: Long) {
        _createTransUiState.value = createTransUiState.value.copy(
            date = text
        )
    }

    fun onAmountChange(text: Int) {
        _createTransUiState.value = createTransUiState.value.copy(
            amount = text
        )
    }

    fun onCategoryChange(text: String) {
        _createTransUiState.value = createTransUiState.value.copy(
            category = text
        )
    }

    fun onAccountChange(wallet: Wallet) {
        _createTransUiState.value = createTransUiState.value.copy(
            account = wallet
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
}