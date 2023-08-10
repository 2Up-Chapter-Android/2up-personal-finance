package com.aicontent.main.presentation.note

import PersonalFinance.features.Main.MR
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun NoteScreen(){
    Text(MR.strings.note_screen.desc().localized())
}