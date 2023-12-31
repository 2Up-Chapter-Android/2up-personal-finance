package com.twoup.personalfinance.transaction.presentation.createTransaction

import PersonalFinance.features.transaction.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.utils.data.fold
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_end_text
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_row
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_start_text
import com.twoup.personalfinance.transaction.presentation.theme.marginStart_createTrans_actionBar_tabName
import com.twoup.personalfinance.transaction.presentation.theme.textSize_transaction_textField
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone

val VeryLightGray = Color(0xFFF5F5F5) // You can adjust the color code as needed

class CreateTransactionScreen(private val id: Long) : Screen {
    @Composable
    override fun Content() {
        CreateTransactionScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateTransactionScreen() {
        val navigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val viewModel = rememberScreenModel { CreateTransViewModel() }
        val getListWalletState = viewModel.getListWalletState.collectAsState()
        val tabList = listOf(
            MR.strings.createTrans_tab_income.desc().localized(),
            MR.strings.createTrans_tab_expense.desc().localized(),
            MR.strings.createTrans_tab_transfer.desc().localized()
        )
        val listWallet = remember { mutableStateOf(mutableListOf<Wallet>()) }
        val categoryExpenses by viewModel.categoryExpenses.collectAsState(emptyList())
        val categoryIncome by viewModel.categoryIncome.collectAsState(emptyList())
        val accounts by viewModel.accounts.collectAsState(emptyList())
        val mainScreen = rememberScreen(MainScreenSharedScreen.MainScreen)

        //date picker
        val time = remember { mutableStateOf(DateTimeUtil.toEpochMillis(DateTimeUtil.now())) }
        val state = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = time.value,
            yearRange = IntRange(1, 10000),
            initialDisplayedMonthMillis = time.value
        )
        val openDialog = remember { mutableStateOf(true) }
        val transactionById by viewModel.transactionById.collectAsState()

        LaunchedEffect(Unit) {
            if (id != -1L) {
                // Editing an existing transaction
                viewModel.loadTransaction()
                viewModel.getTransactionById(id)
                viewModel.updateCreateTransUiState(transactionById)
                Napier.d(
                    "what is transaction ${viewModel.transactionById}",
                    tag = "transactionById"
                )

            } else {
//                 Creating a new transaction
                viewModel.loadTransaction()
            }
        }
        val createTransUiState = viewModel.createTransUiState.collectAsState()
        val selectedTabIndex =
            remember { mutableStateOf(createTransUiState.value.selectIndex) }

        Napier.d("id = $id and $createTransUiState", tag = "id please")
        Napier.d("id = $id and ${viewModel.transactionById.value}", tag = "id please")


        LaunchedEffect(getListWalletState.value) {
            getListWalletState.value.fold(onSuccess = { listWallet.value.addAll(it.data) },
                onFailure = { /* Handle failure */ },
                onLoading = { it?.let { listWallet.value.addAll(it.data) } })
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {
                        navigator.pop()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(marginStart_createTrans_actionBar_tabName))
                    Text(
                        text = tabList[selectedTabIndex.value],
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        contentColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                        divider = { /*remove underline*/ },
                        indicator = { /*remove indicator*/ }) {
                        tabList.forEachIndexed { index, tabName ->
                            tabLayoutTrans(
                                index = index, value = tabName, selectedTabIndex = selectedTabIndex
                            )
                            Napier.d(
                                "the number is ${selectedTabIndex.value}",
                                tag = "selectedTabIndex"
                            )
                        }
                    }
                    when (selectedTabIndex.value) {
                        0 ->
                            TransactionScreen(
                                viewModel = viewModel,
                                openDialog = openDialog,
                                selectIndex = selectedTabIndex,
                                transactionType = TransactionType.Income,
                                id = id.toInt(),
                                createUiState = createTransUiState
                            )

                        1 -> TransactionScreen(
                            viewModel = viewModel,
                            openDialog = openDialog,
                            selectIndex = selectedTabIndex,
                            transactionType = TransactionType.Expense,
                            id = id.toInt(),
                            createUiState = createTransUiState

                        )

                        else -> TransferScreen(
                            viewModel = viewModel,
                            openDialog = openDialog,
                            selectIndex = selectedTabIndex,
                            id = id.toInt(),
                            createTransUiState = createTransUiState
                        )
                    }
                }


                AnimatedVisibility(visible = createTransUiState.value.isOpenChooseWallet || createTransUiState.value.isOpenChooseAccountTo || createTransUiState.value.isOpenChooseAccountFrom) {
                    AccountBottomSheet(
                        accounts = accounts,
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }

                AnimatedVisibility(visible = createTransUiState.value.isOpenChooseCategory) {
                    CategoryBottomSheet(
                        categorys = if (selectedTabIndex.value == 1) {
                            categoryExpenses
                        } else {
                            categoryIncome
                        },
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }

                AnimatedVisibility(visible = createTransUiState.value.isOpenDatePicker) {

                    if (createTransUiState.value.isOpenDatePicker/*openDialog.value*/) {
                        DatePickerDialog(
                            onDismissRequest = { viewModel.openCloseDatePicker(false) /*openDialog.value = false*/ },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        focusManager.clearFocus()
                                        viewModel.openCloseDatePicker(false)
                                        openDialog.value = false
                                        time.value =
                                            state.selectedDateMillis ?: DateTimeUtil.toEpochMillis(
                                                DateTimeUtil.now()
                                            )
                                        viewModel.onDateChange(
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
                                    openDialog.value = false
                                }) {
                                    Text("Cancel", color = Color.Black)
                                }
                            },
                            modifier = Modifier,
                            // use to dismiss dialog
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
            }
        }
    }
}

@Composable
fun tabLayoutTrans(index: Int, value: String, selectedTabIndex: MutableState<Int>) {
    Tab(
        selected = selectedTabIndex.value == index,
        onClick = { selectedTabIndex.value = index },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = {
                selectedTabIndex.value = index
            },
            modifier = Modifier.padding(vertical = create_transaction_padding_row),
            shape = RoundedCornerShape(20),
            border = if (selectedTabIndex.value == index) {
                when (selectedTabIndex.value) {
                    0 -> BorderStroke(thickness_transaction_borderStroke, Color.Blue)
                    1 -> BorderStroke(
                        thickness_transaction_borderStroke,
                        colorResource(MR.colors.createTrans_tab_expense)
                    )

                    else -> BorderStroke(thickness_transaction_borderStroke, Color.Black)
                }
            } else {
                BorderStroke(thickness_transaction_borderStroke, Color.Gray)
            },
            colors = if (selectedTabIndex.value == index) {
                when (selectedTabIndex.value) {
                    0 -> ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Blue, backgroundColor = Color.White
                    )

                    1 -> ButtonDefaults.outlinedButtonColors(
                        contentColor = colorResource(MR.colors.createTrans_tab_expense),
                        backgroundColor = Color.White
                    )

                    else -> ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black, backgroundColor = Color.White
                    )
                }
            } else {
                ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Gray,
                    backgroundColor = colorResource(MR.colors.createTrans_tab_unselected)
                )
            }
        ) {
            Text(
                text = value, style = MaterialTheme.typography.button, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LineTransInformation(
    textLabel: String,
    text: String,
    onTextChange: (String) -> Unit = {},
    keyboardOption: KeyboardOptions,
    readOnly: Boolean = false,
    textFieldModifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(
//            create_transaction_padding_row
//        )
    ) {
        Text(
            text = textLabel,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(
                start = create_transaction_padding_start_text,
                end = create_transaction_padding_end_text
            ).weight(2f),
            fontSize = 12.sp
        )

        TextField(
            modifier = textFieldModifier.fillMaxWidth()
                .padding(end = create_transaction_padding_end_text).weight(8f),
            value = text,
            onValueChange = { onTextChange(it) },
            textStyle = TextStyle(
                fontSize = textSize_transaction_textField
            ),
            singleLine = singleLine,
            readOnly = readOnly,
            keyboardOptions = keyboardOption,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray
            )
        )
    }
}


