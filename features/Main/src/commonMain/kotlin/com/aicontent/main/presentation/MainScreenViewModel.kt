package com.aicontent.main.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.component.KoinComponent

class MainScreenViewModel : ScreenModel, KoinComponent {
    
    var selectedTabIndex: MutableState<Int> = mutableStateOf(0)

}