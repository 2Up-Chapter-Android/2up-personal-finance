package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CreateTransViewModel: ScreenModel, KoinComponent {
    private val getListWalletsUseCase: GetListWalletsUseCase by inject()

    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    private val _getListWalletState = MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
    val getListWalletState = _getListWalletState.asStateFlow()

    init {
        getListWallets()
    }

    private fun getListWallets() {
        GlobalScope.launch {
            getListWalletsUseCase().collect {
                _getListWalletState.emit(it)
            }
//            _getListWalletState.tryEmit(getListWalletsUseCase().first())
        }
    }

    fun onDateChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            date = text
        )
    }

    fun onAmountChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            amount = text
        )
    }

    fun onCategoryChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            category = text
        )
    }

    fun onAccountChange(wallet: Wallet){
        _createTransUiState.value = createTransUiState.value.copy(
            account = wallet
        )
    }

    fun onNoteChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            note = text
        )
    }

    fun openCloseChooseWallet(isOpen: Boolean){
        _createTransUiState.value = createTransUiState.value.copy(
            isOpenChooseWallet = isOpen
        )
    }
}