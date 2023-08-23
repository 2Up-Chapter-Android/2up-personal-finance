package com.aicontent.main.presentation.daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyScreenViewModel : ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    init {
        loadNotes()
    }
    fun loadNotes() {
        useCaseGetAllTransaction.getAllTransaction()
    }
    private val _createTransUiState = MutableStateFlow(TransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    // insert get list transaction


}