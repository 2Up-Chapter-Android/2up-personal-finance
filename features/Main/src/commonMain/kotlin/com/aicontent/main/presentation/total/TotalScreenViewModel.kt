package com.aicontent.main.presentation.total

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.component.KoinComponent

class TotalScreenViewModel : ScreenModel, KoinComponent {
    
    var selectedTabIndex: MutableState<Int> = mutableStateOf(3)

}