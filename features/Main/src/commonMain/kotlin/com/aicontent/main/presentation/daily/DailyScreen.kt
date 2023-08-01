package com.aicontent.main.presentation.daily


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.aicontent.main.presentation.TopAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.aicontent.main.presentation.BudgetBox
import com.aicontent.main.presentation.TapBarViewModel
import io.github.aakira.napier.Napier

class DailyScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { DailyScreenViewModel() }

        Scaffold(
            topBar = {
                TopAppBar(
                    onSearchClicked = {},
                    onBookMark = {},
                    onAnalysis = {},
                    viewModel.selectedTabIndex.value
                )
            },
            content = {
                Column{
                    BudgetBox()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Daily Screen")
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}