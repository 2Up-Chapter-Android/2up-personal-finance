package com.twoup.personalfinance.transaction.presentation.createTransaction

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionRequestModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.CreateTransactionResponseModel
import com.twoup.personalfinance.domain.usecase.transaction.CreateTransactionUseCase
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import com.twoup.personalfinance.remote.util.Resource
import com.twoup.personalfinance.remote.util.toResource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun createTransaction(type: String) {

        _createTransUiState.value = createTransUiState.value.copy(isLoading = true)

        coroutineScope.launch {
            delay(200)

            val createTransReponse = createTransUseCase(
                CreateTransactionRequestModel(
                    amount = createTransUiState.value.amount,
                    categoryId = createTransUiState.value.category,
                    createdAt = createTransUiState.value.date,//
                    description = createTransUiState.value.category,
                    note = createTransUiState.value.note,
                    type = type,
                    updatedAt = createTransUiState.value.date,//
                    walletId = createTransUiState.value.account,
                )
            ).toResource()
            _createTransUiState.value = createTransUiState.value.copy(isLoading = false)
            _createTransState.tryEmit(createTransReponse)

        }
    }


    private fun isValidateInput(
        invalidDateMsg: String,
        invalidAmountMsg: String,
        invalidCategoryErrorMsg: String,
        invalidAccountErrorMsg: String,
        invalidNoteErrorMsg: String
    ): Boolean {

        _createTransUiState.value = createTransUiState.value.copy(
            date = 0,
            amount = 0,
            category = "",
            account = "",
            note = ""
        )
        var isValid = true
//    if (!createTransUiState.value.amount.()) {
//        _createTransUiState.value = createTransUiState.value.copy(
//            amount = invalidAmountMsg
//        )
//        isValid = false
//    }
//
//    if (!createTransUiState.value.category.isNullOrBlank()) {
//        _createTransUiState.value = createTransUiState.value.copy(
//            category = invalidCategoryErrorMsg
//        )
//        isValid = false
//    }
//
//    if (!createTransUiState.value.date.isNullOrBlank()) {
//        _createTransUiState.value = createTransUiState.value.copy(
//            date = invalidDateMsg
//        )
//        isValid = false
//    }
        if (!createTransUiState.value.account.isNullOrBlank()) {
            _createTransUiState.value = createTransUiState.value.copy(
                account = invalidAccountErrorMsg
            )
            isValid = false
        }
        if (!createTransUiState.value.note.isNullOrBlank()) {
            _createTransUiState.value = createTransUiState.value.copy(
                note = invalidNoteErrorMsg
            )
            isValid = false
        }
        return isValid

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
}