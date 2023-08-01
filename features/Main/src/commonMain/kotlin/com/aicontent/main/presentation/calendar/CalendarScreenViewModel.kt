package com.aicontent.main.presentation.calendar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.component.KoinComponent

class CalendarScreenViewModel : ScreenModel, KoinComponent {
    
    var selectedTabIndex: MutableState<Int> = mutableStateOf(1)

}