package com.aicontent.main.presentation.daily.editItem

//import PersonalFinance.features.Main.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
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
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.daily.AccountBottomSheet
import com.aicontent.main.presentation.daily.CategoryBottomSheet
import com.aicontent.main.presentation.daily.DailyScreenViewModel
import com.aicontent.main.theme.create_transaction_padding_end_text
import com.aicontent.main.theme.create_transaction_padding_row
import com.aicontent.main.theme.create_transaction_padding_start_text
import com.aicontent.main.theme.marginStart_createTrans_actionBar_tabName
import com.aicontent.main.theme.textSize_transaction_textField
import com.aicontent.main.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil
//import dev.icerock.moko.resources.compose.colorResource
//import dev.icerock.moko.resources.compose.localized
//import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime


class ItemTransactionScreen(private val transaction: TransactionLocalModel) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { DailyScreenViewModel() }
        val openDialog = remember { mutableStateOf(true) }
        val focusManager = LocalFocusManager.current
        val categoryExpenses by viewModel.categoryExpenses.collectAsState(emptyList())
        val categoryIncome by viewModel.categoryIncome.collectAsState(emptyList())
        val accounts by viewModel.accounts.collectAsState(emptyList())
        val selectedTabIndex = remember { mutableStateOf(transaction.transactionSelectIndex) }
        val interactionSource = remember { MutableInteractionSource() }
        val time = remember { mutableStateOf(DateTimeUtil.toEpochMillis(DateTimeUtil.now())) }
        val state = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = time.value
        )
        val tabList = listOf(
//            MR.strings.createTrans_tab_income.desc().localized(),
//            MR.strings.createTrans_tab_expense.desc().localized(),
//            MR.strings.createTrans_tab_transfer.desc().localized()
            "Income", "Expense", "Transfer"
        )

        LaunchedEffect(Unit) {
            viewModel.loadTransaction()
            viewModel.updateTransactionUiState(transaction)
        }
        val transactionUiState = viewModel.transactionUiState.collectAsState().value

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { navigator.pop() }) {
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
                                index = index,
                                value = tabName,
                                selectedTabIndex = selectedTabIndex,
                                viewModel = viewModel
                            )
                            Napier.d(
                                "the number is ${selectedTabIndex.value}",
                                tag = "selectedTabIndex"
                            )
                        }
                    }

                    Napier.d(
                        "the number test in edit screen ${selectedTabIndex.value}",
                        tag = "selectedTabIndex"
                    )

                    when (selectedTabIndex.value) {
                        0 -> TransactionScreen(
                            viewModel = viewModel,
                            openDialog = openDialog,
                            transaction = transaction,
                            selectIndex = selectedTabIndex,
                            transactionType = TransactionType.INCOME
                        )

                        1 -> TransactionScreen(
                            viewModel = viewModel,
                            openDialog = openDialog,
                            transaction = transaction,
                            selectIndex = selectedTabIndex,
                            transactionType = TransactionType.EXPENSES
                        )

                        2 -> TransactionScreen(
                            viewModel = viewModel,
                            openDialog = openDialog,
                            transaction = transaction,
                            selectIndex = selectedTabIndex,
                            transactionType = TransactionType.TRANSFER
                        )
                    }

                    // Common UI components shared across all transaction types

                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
//            .padding(
//                start = create_transaction_spacer_padding_horizontal,
//                end = create_transaction_spacer_padding_horizontal,
//                top = create_transaction_spacer_padding_top,
//                bottom = create_transaction_spacer_padding_bottom
//            )
                            .fillMaxWidth()
//                            .background(colorResource(MR.colors.createTrans_line_break)),
                            .background(
//                                Color.Red
                                Color(0xdedee1)
                            )
//                            .background(colorResource(MR.colors.createTrans_line_break)),
                    )
                    // AnimatedVisibility for the remaining buttons
                    AnimatedVisibility(
                        visible = !transactionUiState.showSaveButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Delete Button
                            TransactionButton(
                                text = "Delete",
                                icon = Icons.Default.Delete
                            ) {
                                transactionUiState.id.let { viewModel.deleteTransactionById(it) }
                                navigator.pop()
                            }
                            val itemTransactionScreen = rememberScreen(
                                TransactionSharedScreen.CreateTransactionScreen(transaction.transactionId)
                            )
//                            val itemTransactionScreen = rememberScreen(TransactionSharedScreen.CreateTransactionScreen(transaction.transaction_id))

                            // Copy Button
                            TransactionButton(
                                text = "Copy",
                                icon = Icons.Default.Create
                            ) {
                                navigator.push(itemTransactionScreen)
                            }

                            // Bookmark Button
                            TransactionButton(
                                text = "Bookmark",
                                icon = Icons.Default.Star
                            ) {
                                // Handle bookmark button click
                            }
                        }
                    }
                    // Save Button
                    AnimatedVisibility(
                        visible = transactionUiState.showSaveButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Button(
                            onClick = {
                                viewModel.updateTransaction(
                                    TransactionLocalModel(
                                        transactionId = transactionUiState.id,
                                        transactionIncome = transactionUiState.income,
                                        transactionExpenses = transactionUiState.expenses,
                                        transactionTransfer = transactionUiState.transfer,
                                        transactionDescription = transactionUiState.description,
                                        transactionNote = transactionUiState.note,
                                        transactionCreated = transactionUiState.date,
                                        transactionMonth = transaction.transactionMonth,
                                        transactionYear = transaction.transactionYear,
                                        transactionCategory = transactionUiState.category,
                                        transactionAccount = transactionUiState.account,
                                        transactionSelectIndex = transactionUiState.selectIndex,
                                        transactionAccountFrom = transactionUiState.accountFrom,
                                        transactionAccountTo = transactionUiState.accountTo
                                    )
                                )
                                viewModel.loadTransaction()
                                navigator.pop()
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = colorResource(MR.colors.createTrans_tab_expense)
                                backgroundColor = Color.Red

                            )
                        ) {
                            Text("Save", color = Color.White)
                        }
                    }
                }
                Napier.d(
                    "is open choose wallet ${transactionUiState.isOpenChooseWallet}",
                    tag = "wallet"
                )

                AnimatedVisibility(visible = transactionUiState.isOpenChooseWallet || transactionUiState.isOpenChooseAccountTo || transactionUiState.isOpenChooseAccountFrom) {
                    AccountBottomSheet(
                        accounts = accounts,
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }

                AnimatedVisibility(visible = transactionUiState.isOpenChooseCategory) {
                    CategoryBottomSheet(
                        category = if (selectedTabIndex.value == 1) {
                            categoryExpenses
                        } else {
                            categoryIncome
                        },
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }

                AnimatedVisibility(visible = transactionUiState.isOpenDatePicker) {
                    if (transactionUiState.isOpenDatePicker/*openDialog.value*/) {
                        DatePickerDialog(onDismissRequest = { viewModel.openCloseDatePicker(false) /*openDialog.value = false*/ },
                            confirmButton = {
                                TextButton(onClick = {
                                    focusManager.clearFocus()
                                    viewModel.openCloseDatePicker(false)
                                    openDialog.value = false
                                    time.value =
                                        state.selectedDateMillis ?: DateTimeUtil.toEpochMillis(
                                            DateTimeUtil.now()
                                        )
                                    viewModel.onDateChange(
                                        Instant.fromEpochMilliseconds(time.value)
                                            .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                                    )
                                    viewModel.updateShowSaveButton()
                                }) {
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
                            }
                        ) {
                            DatePicker(
                                state = state, title = {
                                    Text(
                                        text = "Choose Your Date", modifier = Modifier.padding(
                                            start = 24.dp, end = 12.dp, top = 16.dp
                                        )
                                    )
                                }, showModeToggle = false
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LineTransInfor(
    textLabel: String,
    text: String,
    onTextChange: (String) -> Unit = {},
    keyboardOption: KeyboardOptions,
    readOnly: Boolean = false,
    textFieldModifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
            create_transaction_padding_row
        )
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
            singleLine = true,
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

@Composable
fun tabLayoutTrans(
    index: Int,
    value: String,
    selectedTabIndex: MutableState<Int>,
    viewModel: DailyScreenViewModel
) {
    Tab(
        selected = selectedTabIndex.value == index,
        onClick = { selectedTabIndex.value = index },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = {
                selectedTabIndex.value = index
                viewModel.updateShowSaveButton()
            },
            modifier = Modifier.padding(vertical = create_transaction_padding_row),
            shape = RoundedCornerShape(20),
            border = if (selectedTabIndex.value == index) {
                when (selectedTabIndex.value) {
                    0 -> BorderStroke(thickness_transaction_borderStroke, Color.Blue)
                    1 -> BorderStroke(
                        thickness_transaction_borderStroke,
//                        colorResource(MR.colors.createTrans_tab_expense)
//                        Color(0xff603b)
                        Color.Blue


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
                        contentColor = Color.Blue, backgroundColor = Color.White
                    )

                    else -> ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black, backgroundColor = Color.White
                    )
                }
            } else {
                ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Gray,
                    backgroundColor = Color.White
                )
            }
        ) {
            Text(
                text = value,
                style = typography.button,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
