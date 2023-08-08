package com.aicontent.main.presentation.daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class DailyScreenViewModel : ScreenModel, KoinComponent {
    
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)

    private val _createTransUiState = MutableStateFlow(TransUiState())
    val createTransUiState = _createTransUiState.asStateFlow()

    // insert get list transaction


}