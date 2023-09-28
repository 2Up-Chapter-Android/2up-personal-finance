package com.aicontent.main.presentation.daily.editItem

import PersonalFinance.features.Main.MR
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
    val transactionUiState = viewModel.transactionUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.loadTransaction()
    }
    when (transactionType) {
        TransactionType.EXPENSES -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transaction.transaction_created),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = { viewModel.onDateChange(transaction.transaction_created) },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_account,
                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_category,
                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = transaction.transaction_expenses.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = { viewModel.onExpensesChange(it) },
            )

            LineTransInfor(
                text = transaction.transaction_description,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = { viewModel.onNoteChange(it) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
        }

        TransactionType.INCOME -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transaction.transaction_created),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = { viewModel.onDateChange(transaction.transaction_created) },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_account,
                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_category,
                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = transaction.transaction_income.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = { viewModel.onIncomeChange(it) },
            )

            LineTransInfor(
                text = transaction.transaction_description,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = { viewModel.onNoteChange(it) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
        }

        TransactionType.TRANSFER -> {
            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                LineTransInfor(
                    text = DateTimeUtil.formatNoteDate(transaction.transaction_created),
                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    onTextChange = { viewModel.onDateChange(transaction.transaction_created) },
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseDatePicker(it.hasFocus)
                        openDialog.value = it.hasFocus
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_accountFrom,
                    textLabel = MR.strings.createTrans_inputLabel_from.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountFrom(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transaction.transaction_accountTo,
                    textLabel = MR.strings.createTrans_inputLabel_to.desc().localized(),
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountTo(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = transaction.transaction_transfer.toString(),
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = { viewModel.onTransferChange(it) },
            )

            LineTransInfor(
                text = transaction.transaction_description,
                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                onTextChange = { viewModel.onNoteChange(it) },
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
    // Inside your Row composable
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
            transaction.transaction_id?.let { viewModel.deleteTransactionById(it) }
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
