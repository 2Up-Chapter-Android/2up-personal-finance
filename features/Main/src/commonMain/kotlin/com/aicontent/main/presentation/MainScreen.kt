package com.aicontent.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.calendar.CalenderScreen
import com.aicontent.main.presentation.daily.DailyScreen
import com.aicontent.main.presentation.monthly.MonthlyScreen
import com.aicontent.main.presentation.note.NoteScreen
import com.aicontent.main.presentation.total.TotalScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen

class MainScreen() : Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { MainScreenViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val transactionScreen = rememberScreen(TransactionSharedScreen.CreateTransactionScreen)

        Scaffold(
            topBar = {
                TopAppBar(
                    onSearchClicked = {},
                    onBookMark = {},
                    onAnalysis = {},
                    viewModel = viewModel
                )
            },
            content = {
                Column {
                    BudgetBox()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (viewModel.selectedTabIndex.value) {
                            0 -> DailyScreen()
                            1 -> CalenderScreen()
                            4 -> MonthlyScreen()
                            3 -> TotalScreen()
                            else -> NoteScreen()
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(transactionScreen)

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
