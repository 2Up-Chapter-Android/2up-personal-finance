package com.aicontent.main.presentation

//import CalendarView
import CalendarView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.calendar.CalenderScreen
import com.aicontent.main.presentation.daily.DailyScreen
import com.aicontent.main.presentation.daily.DailyScreenViewModel
import com.aicontent.main.presentation.monthly.MonthlyScreen
import com.aicontent.main.presentation.monthly.MonthlyScreenViewModel
import com.aicontent.main.presentation.note.NoteScreen
import com.aicontent.main.presentation.total.TotalScreen
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainScreen() : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { MainScreenViewModel() }
        val viewModelDailyScreen = rememberScreenModel { DailyScreenViewModel() }
        val viewModelMonthLyScreen = rememberScreenModel { MonthlyScreenViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val transactionScreen = rememberScreen(
            TransactionSharedScreen.CreateTransactionScreen(id = -1)
        )
        val searchScreen = rememberScreen(MainScreenSharedScreen.SearchScreen)

        val listTransactionState = viewModel.getListTransactionState.collectAsState()
        val listTransaction = remember { mutableStateOf(mutableListOf<TransactionEntity>()) }
        val monthYear = viewModel.currentMonthYear
        val transactionByMonth = viewModel.transactionByMonth.collectAsState().value
        val transactionByYear = viewModel.transactionByYear.collectAsState().value
        var openDiaLog = viewModel.openDiaLog.value
        val time = remember { mutableStateOf(DateTimeUtil.toEpochMillis(DateTimeUtil.now())) }
        val state = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = time.value,
            yearRange = IntRange(1, 10000),
            initialDisplayedMonthMillis = time.value
        )
        val focusManager = LocalFocusManager.current
// Trigger a LaunchedEffect to load and filter transactions based on the selected month and year.
        LaunchedEffect(Unit) {
            viewModel.loadTransaction()
            viewModel.filterTransactionByMonth(monthYear.value.month, monthYear.value.year)
            viewModel.filterTransactionByYear(monthYear.value.year)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    onSearchClicked = { navigator.push(searchScreen) },
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
                            0 -> DailyScreen(viewModelDailyScreen, transactionByMonth, viewModel)
                            1 -> CalendarView(viewModel)
                            2 -> MonthlyScreen(viewModelMonthLyScreen, transactionByYear, viewModel)
                            3 -> TotalScreen()
                            else -> NoteScreen()
                        }
                    }

                    AnimatedVisibility(viewModel.openDiaLog.value) {
                        DatePickerDialog(
                            onDismissRequest = { viewModel.openCloseDatePicker(false) },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        focusManager.clearFocus()
                                        viewModel.openCloseDatePicker(false)
                                        openDiaLog = false
                                        time.value =
                                            state.selectedDateMillis ?: DateTimeUtil.toEpochMillis(
                                                DateTimeUtil.now()
                                            )
                                        viewModel.onDateMonthChange(
                                            Instant.fromEpochMilliseconds(time.value)
                                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                        )
                                    }
                                ) {
                                    Text("OK", color = Color.Black)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    focusManager.clearFocus()
                                    viewModel.openCloseDatePicker(false)
                                    openDiaLog = false
                                }) {
                                    Text("Cancel", color = Color.Black)
                                }
                            },
                        properties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true,
                            usePlatformDefaultWidth = true,
                        )
                        ) {
                            Column {
                                DatePicker(
                                    state = state,
                                    title = {
                                        Text(
                                            text = "Choose Your Date",
                                            modifier = Modifier.padding(
                                                start = 24.dp,
                                                end = 12.dp,
                                                top = 16.dp
                                            )
                                        )
                                    },
                                    // under the title
//                                headline = {
//                                    Text(
//                                        "hmmm",
//                                        modifier = Modifier.padding(
//                                            start = 24.dp,
//                                            end = 12.dp,
//                                            top = 16.dp
//                                        )
//                                    )
//                                },
//                                dateFormatter = DatePickerFormatter(
//                                    yearSelectionSkeleton = "March 2021",
////                                    selectedTabIndex = "Mar 27, 2021",
////                                    selectedDateDescriptionSkeleton = "Saturday, March 27, 2021"
//                                ),
                                    showModeToggle = false
                                )
                            }
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
