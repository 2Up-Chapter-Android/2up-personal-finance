package com.aicontent.main.presentation.daily

import PersonalFinance.features.Main.MR
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
import androidx.compose.material.Card
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.theme.create_transaction_padding_end_text
import com.aicontent.main.theme.create_transaction_padding_row
import com.aicontent.main.theme.create_transaction_padding_start_text
import com.aicontent.main.theme.textSize_transaction_textField
import com.aicontent.main.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil
import com.twoup.personalfinance.utils.data.fold
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent

class ItemTransactionScreen(private val transaction: TransactionLocalModel) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { DailyScreenViewModel() }
        val transactionUiState = viewModel.transactionUiState.value
        val openDialog = remember { mutableStateOf(true) }
        val focusManager = LocalFocusManager.current
        val categorys = viewModel.categorys.value
        val accounts = viewModel.accounts.value
        val interactionSource = remember { MutableInteractionSource() }
        val time = remember { mutableStateOf(DateTimeUtil.toEpochMillis(DateTimeUtil.now())) }
        val state = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = time.value
        )

//        transactionUiState.let { transUiState ->
//            transUiState.date = transaction.created
//            transUiState.note = transaction.description
//            transUiState.account = transaction.account
//            transUiState.category = transaction.category
//            transUiState.amount = transaction.amount.toString()
////            transUiState. = transaction.description
//        }
        Column(modifier = Modifier.padding(16.dp)) {

            LineTransInfor(
                text = DateTimeUtil.formatNoteDate(transaction.created),
                textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                onTextChange = { viewModel.onDateChange(transactionUiState.date) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
//                readOnly = true,
                textFieldModifier = Modifier.onFocusChanged {
                    viewModel.openCloseDatePicker(it.hasFocus)
                    openDialog.value = it.hasFocus
                }
            )


            LineTransInfor(
                text = transaction.account,
                textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
//                readOnly = true,
                textFieldModifier = Modifier.onFocusChanged {
                    viewModel.openCloseChooseWallet(it.hasFocus)
                }
            )

            LineTransInfor(
                text = transaction.category,
                textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
//                readOnly = true,
                textFieldModifier = Modifier.onFocusChanged {
                    viewModel.openCloseChooseCategory(it.hasFocus)
                }
            )

            LineTransInfor(
                text = transaction.amount.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = { viewModel.onAmountChange(it) },
            )

            LineTransInfor(
                text = transaction.description,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = { viewModel.onNoteChange(it) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            Button(onClick = { navigator.pop() }) {
                Text(text = "Go Back")
            }

            AnimatedVisibility(visible = transactionUiState.isOpenChooseWallet) {
                AccountBottomSheet(
                    focusManager = focusManager,
                    accounts = accounts,
                    viewModel = viewModel,
                    interactionSource = interactionSource
                )
            }

            AnimatedVisibility(visible = transactionUiState.isOpenChooseCategory) {
                CategoryBottomSheet(
                    focusManager = focusManager,
                    categorys = categorys,
                    viewModel = viewModel,
                    interactionSource = interactionSource
                )
            }

            AnimatedVisibility(visible = transactionUiState.isOpenDatePicker) {
                if (transactionUiState.isOpenDatePicker/*openDialog.value*/) {
                    DatePickerDialog(
                        onDismissRequest = { viewModel.openCloseDatePicker(false) /*openDialog.value = false*/ },
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
                            }) {
                                Text("OK", color = Color.Black)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    focusManager.clearFocus()
                                    viewModel.openCloseDatePicker(false)
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
                                    modifier = Modifier.padding(
                                        start = 24.dp,
                                        end = 12.dp,
                                        top = 16.dp
                                    )
                                )
                            },
                            showModeToggle = false
                        )
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


