package com.aicontent.main.presentation.total

import PersonalFinance.features.Main.MR
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun TotalScreen(){
    Text(MR.strings.total_screen.desc().localized())
}