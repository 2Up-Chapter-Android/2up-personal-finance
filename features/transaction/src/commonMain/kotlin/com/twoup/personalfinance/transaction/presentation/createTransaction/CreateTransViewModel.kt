package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CreateTransViewModel: ScreenModel, KoinComponent {
    private val getListWalletsUseCase: GetListWalletsUseCase by inject()

    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    init {
        GlobalScope.launch {
            getListWalletsUseCase().fold(
                onSuccess = {
                    Napier.d(tag = "TestGetWallet", message = it.data.toString())
                },
                onFailure = {
                    Napier.d(tag = "TestGetWallet", message = it.message.toString())
                }
            )
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

    fun onAccountChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            account = text
        )
    }

    fun onNoteChange(text: String){
        _createTransUiState.value = createTransUiState.value.copy(
            note = text
        )
    }
}