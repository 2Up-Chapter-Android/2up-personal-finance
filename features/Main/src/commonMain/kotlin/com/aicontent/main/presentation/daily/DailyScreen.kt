package com.aicontent.main.presentation.daily

import PersonalFinance.features.Main.MR
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun DailyScreen(){
    Text(MR.strings.daily_screen.desc().localized())
}