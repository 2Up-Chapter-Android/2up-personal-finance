package com.aicontent.main.presentation.monthly

import PersonalFinance.features.Main.MR
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun MonthlyScreen(){
    Text(MR.strings.monthly_screen.desc().localized())
}