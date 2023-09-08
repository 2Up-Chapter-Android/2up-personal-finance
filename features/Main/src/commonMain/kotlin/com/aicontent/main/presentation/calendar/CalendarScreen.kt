package com.aicontent.main.presentation.calendar

import PersonalFinance.features.Main.MR
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderScreen() {
    val time = remember { mutableStateOf(DateTimeUtil.toEpochMillis(DateTimeUtil.now())) }
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = time.value
    )
    Text(MR.strings.calendar_screen.desc().localized())

    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    time.value = state.selectedDateMillis ?: DateTimeUtil.toEpochMillis(DateTimeUtil.now())
                }) {
                    Text("OK", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel", color = Color.Black)
                }
            }
        ) {
//            TODO: DatePicker dang bi bug, neu ranh thi tu code headline cua minh, khong dung headline mac dinh cua DatePicker
            DatePicker(
                state = state,
                title = {
                    Text(
                        text = "Choose Your Date",
                        modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
                    )
                },
                showModeToggle = false
            )
        }
    }
}