package com.aicontent.main.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.getTransaction.GetAllTransactionsResponseModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.transaction.GetListTransactionUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreenViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()
    private val getListTransactionUseCase: GetListTransactionUseCase by inject()

    private val _getListTransactionState = MutableStateFlow<Resource<GetAllTransactionsResponseModel>>(Resource.loading())
    val getListTransactionState = _getListTransactionState.asStateFlow()
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    init {
        loadNotes()
        getListTransaction()
    }
     fun loadNotes() {
        useCaseGetAllTransaction.getAllTransaction()
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun getListTransaction() {
        GlobalScope.launch {
            getListTransactionUseCase.invoke().collect {
                _getListTransactionState.emit(it)
            }
        }
    }
}