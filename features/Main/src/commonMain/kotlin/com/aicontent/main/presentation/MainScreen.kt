package com.aicontent.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.calendar.CalenderScreen
import com.aicontent.main.presentation.daily.DailyScreen
import com.aicontent.main.presentation.daily.DailyScreenViewModel
import com.aicontent.main.presentation.monthly.MonthlyScreen
import com.aicontent.main.presentation.note.NoteScreen
import com.aicontent.main.presentation.total.TotalScreen
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import com.twoup.personalfinance.navigation.TransactionSharedScreen

class MainScreen() : Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { MainScreenViewModel() }
        val viewModelDailyScreen = rememberScreenModel { DailyScreenViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val transactionScreen = rememberScreen(
            TransactionSharedScreen.CreateTransactionScreen( id = -1)
        )

        val listTransactionState = viewModel.getListTransactionState.collectAsState()
        val listTransaction = remember { mutableStateOf(mutableListOf<TransactionEntity>()) }
        val monthYear = viewModel.currentMonthYear
        val transactionByMonth = viewModel.transactionByMonth.collectAsState().value

        // Trigger a LaunchedEffect to load and filter transactions based on the selected month and year.
        LaunchedEffect(Unit) {
            viewModel.loadTransaction()
            viewModel.filterTransactionByMonth(monthYear.value.month, monthYear.value.year)
        }

//        LaunchedEffect(listTransactionState.value) {
//            listTransactionState.value.fold(
//                onSuccess = {
//                    Napier.d(tag = "MainScreen", message = "Get list transaction success $it")
//                    listTransaction.value.clear()
//                    listTransaction.value.addAll(it.data)
//                },
//                onFailure = {
//                    Napier.d(tag = "MainScreen", message = "Get list transaction failed $it")
//                },
//                onLoading = {
//                    Napier.d(tag = "MainScreen", message = "Get list transaction loading $it")
//                    it?.data?.let { it1 ->
//                        listTransaction.value.clear()
//                        listTransaction.value.addAll(it1)
//                    }
//                }
//            )
//        }

        Scaffold(
            topBar = {
                TopAppBar(
                    onSearchClicked = {},
                    onBookMark = {},
                    onAnalysis = {},
                    viewModel = viewModel,
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 56.dp), // Adjust the bottom padding to match BottomAppBar height
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BudgetBox(viewModel = viewModel)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        when (viewModel.selectedTabIndex.value) {
                            0 -> DailyScreen(viewModelDailyScreen, transactionByMonth)
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
                    modifier = Modifier.padding(bottom = 50.dp)
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
