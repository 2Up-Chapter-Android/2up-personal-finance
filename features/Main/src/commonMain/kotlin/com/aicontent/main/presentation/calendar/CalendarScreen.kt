package com.aicontent.main.presentation.calendar

import PersonalFinance.features.Main.MR
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun CalenderScreen(){
    Text(MR.strings.calendar_screen.desc().localized())
}