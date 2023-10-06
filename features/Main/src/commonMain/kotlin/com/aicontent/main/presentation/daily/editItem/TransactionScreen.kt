package com.aicontent.main.presentation.daily.editItem

import PersonalFinance.features.Main.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.aicontent.main.theme.create_transaction_spacer_padding_bottom
import com.aicontent.main.theme.create_transaction_spacer_padding_horizontal
import com.aicontent.main.theme.create_transaction_spacer_padding_top
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.daily.DailyScreenViewModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.utils.DateTimeUtil
import io.github.aakira.napier.Napier

enum class TransactionType {
    INCOME,
    EXPENSES,
    TRANSFER
}

@Composable
fun TransactionScreen(
    viewModel: DailyScreenViewModel,
    openDialog: MutableState<Boolean>,
    selectIndex: MutableState<Int>,
    transaction: TransactionLocalModel,
    transactionType: TransactionType // You can define this enum or sealed class
) {
    val navigator = LocalNavigator.currentOrThrow
    val transactionUiState = viewModel.transactionUiState.collectAsState().value

    Napier.d("transactionUiState =  $transactionUiState", tag = "transactionUiState")

    LaunchedEffect(Unit) {
        viewModel.loadTransaction()
        viewModel.updateTransactionUiState(transaction)
    }

    when (transactionType) {
        TransactionType.EXPENSES -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transactionUiState.date),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = {
                        viewModel.onDateChange(transactionUiState.date)
                    },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )
                Napier.d(
                    "openCloseDatePicker =  ${transactionUiState.isOpenDatePicker}",
                    tag = "isOpenDatePicker"
                )

                LineTransInfor(
                    text = transactionUiState.account,
                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

//                Napier.d("is open choose wallet ${}", tag = "isOpenChooseWallet")

                LineTransInfor(
                    text = transactionUiState.category,
                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }

            LineTransInfor(
                text = transactionUiState.expenses.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = {
                    viewModel.onExpensesChange(it)
                    viewModel.updateShowSaveButton()
                },
            )
            Napier.d("hmmmm =  ${transactionUiState.expenses}", tag = "transaction  item ")

            LineTransInfor(
                text = transactionUiState.note,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                onTextChange = {
                    viewModel.onDescriptionChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
        }

        TransactionType.INCOME -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transactionUiState.date),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = { viewModel.onDateChange(transactionUiState.date) },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )

                LineTransInfor(
                    text = transactionUiState.account,
                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transactionUiState.category,
                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = "${transactionUiState.income}",
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = {
                    viewModel.onIncomeChange(it.toLong())
                    viewModel.updateShowSaveButton()
                },
            )

            LineTransInfor(
                text = transactionUiState.note,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                onTextChange = {
                    viewModel.onDescriptionChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
        }

        TransactionType.TRANSFER -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transactionUiState.date),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = { viewModel.onDateChange(transactionUiState.date) },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )

                LineTransInfor(
                    text = transactionUiState.accountFrom,
                    textLabel = MR.strings.createTrans_inputLabel_from.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountFrom(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transactionUiState.accountTo,
                    textLabel = MR.strings.createTrans_inputLabel_to.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountTo(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = transactionUiState.transfer.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = {
                    viewModel.onTransferChange(it)
                    viewModel.updateShowSaveButton()
                },
            )

            LineTransInfor(
                text = transactionUiState.note,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                onTextChange = {
                    viewModel.onDescriptionChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
        }
    }

    // Common UI components shared across all transaction types

    Spacer(
        modifier = Modifier
            .height(40.dp)
            .padding(
                start = create_transaction_spacer_padding_horizontal,
                end = create_transaction_spacer_padding_horizontal,
                top = create_transaction_spacer_padding_top,
                bottom = create_transaction_spacer_padding_bottom
            )
            .fillMaxWidth()
            .background(colorResource(MR.colors.createTrans_line_break)),
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
                .padding(2.dp)
        ) {
            // Delete Button
            TransactionButton(
                text = "Delete",
                icon = Icons.Default.Delete
            ) {
                transactionUiState.id.let { viewModel.deleteTransactionById(it) }
                navigator.pop()
            }

            // Copy Button
            TransactionButton(
                text = "Copy",
                icon = Icons.Default.Create
            ) {
                // Handle copy button click
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
                        transaction_id = transactionUiState.id,
                        transaction_income = transactionUiState.income,
                        transaction_expenses = transactionUiState.expenses,
                        transaction_transfer = transactionUiState.transfer,
                        transaction_description = transactionUiState.description,
                        transaction_note = transactionUiState.note,
                        transaction_created = transactionUiState.date,
                        transaction_category = transactionUiState.category,
                        transaction_account = transactionUiState.account,
                        transaction_selectIndex = transactionUiState.selectIndex,
                        transaction_accountFrom = transactionUiState.accountFrom,
                        transaction_accountTo = transactionUiState.accountTo
                    )
                )
                navigator.pop()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(MR.colors.createTrans_tab_expense))
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
fun TransactionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(116.dp)
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20)
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text, // Set a proper content description
                tint = Color.Black // Customize the icon color
            )
            Text(
                text = text,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}
