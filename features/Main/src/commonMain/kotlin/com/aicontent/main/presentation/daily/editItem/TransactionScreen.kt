package com.aicontent.main.presentation.daily.editItem

//import PersonalFinance.features.Main.MR
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
//import dev.icerock.moko.resources.compose.colorResource
//import dev.icerock.moko.resources.compose.localized
//import dev.icerock.moko.resources.desc.desc
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
//                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    textLabel = "Date",
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
//                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    textLabel = "Account",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

//                Napier.d("is open choose wallet ${}", tag = "isOpenChooseWallet")

                LineTransInfor(
                    text = transactionUiState.category,
//                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    textLabel = "Category",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }

            LineTransInfor(
                text = transactionUiState.expenses.toString(),
//                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                textLabel = "Amount",
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
//                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                textLabel = "Note",
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
//                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                textLabel = "Description",
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
//                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    textLabel = "Date",
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
//                    textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                    textLabel = "Account",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseWallet(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transactionUiState.category,
//                    textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                    textLabel = "Category",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategory(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = "${transactionUiState.income}",
//                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                textLabel = "Amount",
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onTextChange = {
                    viewModel.onIncomeChange(it)
                    viewModel.updateShowSaveButton()
                },
            )

            LineTransInfor(
                text = transactionUiState.note,
//                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                textLabel = "Note",
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
//                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                textLabel = "Description",
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
//                    textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                    textLabel = "Date",
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
//                    textLabel = MR.strings.createTrans_inputLabel_from.desc().localized(),
                    textLabel = "From",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountFrom(it.hasFocus)
                    }
                )

                LineTransInfor(
                    text = transactionUiState.accountTo,
//                    textLabel = MR.strings.createTrans_inputLabel_to.desc().localized(),
                    textLabel = "To",
                    keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                    readOnly = true,
                    textFieldModifier = Modifier.onFocusChanged {
                        viewModel.openCloseChooseCategoryAccountTo(it.hasFocus)
                    }
                )
            }
            LineTransInfor(
                text = transactionUiState.transfer.toString(),
//                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                textLabel = "Amount",
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
//                textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                textLabel = "Note",
                onTextChange = {
                    viewModel.onNoteChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = transactionUiState.description,
//                textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
                textLabel = "Description",
                onTextChange = {
                    viewModel.onDescriptionChange(it)
                    viewModel.updateShowSaveButton()
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )
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
