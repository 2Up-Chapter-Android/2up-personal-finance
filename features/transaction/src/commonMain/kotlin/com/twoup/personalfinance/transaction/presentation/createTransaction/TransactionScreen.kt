package com.twoup.personalfinance.transaction.presentation.createTransaction

import PersonalFinance.features.transaction.MR
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.transaction.presentation.theme.buttonHeight_transaction_buttonNextAction
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_row
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier

@Composable
fun TransactionScreen(
    viewModel: CreateTransViewModel,
    openDialog: MutableState<Boolean>,
    selectIndex: MutableState<Int>,
    id: Int,
    transactionType: TransactionType,
    createUiState: State<CreateTransUiState>,
    ) {
    val transactionById by viewModel.transactionById.collectAsState()

    LaunchedEffect(Unit) {
        if (id != -1) {
            // Editing an existing transaction
            viewModel.loadTransaction()
            viewModel.getTransactionById(id.toLong())
            viewModel.updateCreateTransUiState(transactionById)
            viewModel.getTransactionById(id.toLong())
            viewModel.updateCreateTransUiState(transactionById)
//            Napier.d("what is transaction ${viewModel.transactionById.value}", tag = "transactionById")
        } else {
            // Creating a new transaction
            viewModel.loadTransaction()
        }
    }
    val createTransUiState = createUiState.value
//    val createTransUiState = viewModel.createTransUiState.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow

    LineTransInformation(
        text = DateTimeUtil.formatNoteDate(createTransUiState.date),
        textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
        onTextChange = { viewModel.onDateChange(createTransUiState.date) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        readOnly = true,
        textFieldModifier = Modifier.onFocusChanged {
            viewModel.openCloseDatePicker(it.hasFocus)
            openDialog.value = it.hasFocus
        }
    )

    val transactionAmount: String
    val onAmountChange: (String) -> Unit

    when (transactionType) {
        TransactionType.Expense -> {
            transactionAmount = createTransUiState.expenses.toString()
            onAmountChange = { viewModel.onExpensesChange(it) }
        }

        TransactionType.Income -> {
            transactionAmount = createTransUiState.income.toString()
            onAmountChange = { viewModel.onIncomeChange(it) }
        }

        TransactionType.Transfer -> {
            transactionAmount = createTransUiState.transfer.toString()
            onAmountChange = { viewModel.onTransferChange(it) }
        }
    }
    CompositionLocalProvider(
        LocalTextInputService provides null
    ) {
        LineTransInformation(
            text = createTransUiState.account,
            textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            readOnly = true,
            textFieldModifier = Modifier.onFocusChanged {
                viewModel.openCloseChooseWallet(it.hasFocus)
            }
        )
//        command + shift + ^
        LineTransInformation(
            text = createTransUiState.category,
            textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            readOnly = true,
            textFieldModifier = Modifier.onFocusChanged {
                viewModel.openCloseChooseCategory(it.hasFocus)
            }
        )
    }

    // loai bo ky tu thua
    LineTransInformation(
        text = transactionAmount ?: "",
        textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
        keyboardOption = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        onTextChange = { newText ->
            val regex = Regex("[0-9]*") // This regex allows only digits (0-9)
            if (regex.matches(newText)) {
                onAmountChange(newText)
            }
        }
    )

    LineTransInformation(
        text = createTransUiState.note,
        textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
        onTextChange = { viewModel.onNoteChange(it) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
    )

    LineTransInformation(
        text = createTransUiState.description,
        textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
        onTextChange = { viewModel.onDescriptionChange(it) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = false
    )

    Spacer(modifier = Modifier.padding(8.dp))

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
            .background(colorResource(MR.colors.createTrans_line_break)),
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(create_transaction_padding_horizontal)
    ) {
        // Create this transaction and back to the dashboard screen
        Button(
            onClick = {
                val transaction = when (transactionType) {
                    TransactionType.Expense -> {
                        TransactionLocalModel(
                            transactionId = createTransUiState.id,
                            transactionIncome = 0,
                            transactionExpenses = createTransUiState.expenses,
                            transactionTransfer = 0,
                            transactionDescription = createTransUiState.description,
                            transactionNote = createTransUiState.note,
                            transactionCreated = createTransUiState.date,
                            transactionMonth = createTransUiState.date.month.ordinal.toLong() + 1,
                            transactionYear = createTransUiState.date.year.toLong(),
                            transactionCategory = createTransUiState.category,
                            transactionAccount = createTransUiState.account,
                            transactionSelectIndex = selectIndex.value,
                            transactionAccountFrom = "",
                            transactionAccountTo = ""
                        )

                    }

                    TransactionType.Income -> {
                        TransactionLocalModel(
                            transactionId = createTransUiState.id,
                            transactionIncome = createTransUiState.income,
                            transactionExpenses = 0,
                            transactionTransfer = 0,
                            transactionNote = createTransUiState.note,
                            transactionDescription = createTransUiState.description,
                            transactionCreated = createTransUiState.date,
                            transactionMonth = createTransUiState.date.month.ordinal.toLong() + 1,
                            transactionYear = createTransUiState.date.year.toLong(),
                            transactionCategory = createTransUiState.category,
                            transactionAccount = createTransUiState.account,
                            transactionSelectIndex = selectIndex.value,
                            transactionAccountFrom = "",
                            transactionAccountTo = ""
                        )
                    }

                    TransactionType.Transfer -> {
                        TransactionLocalModel(
                            transactionId = createTransUiState.id,
                            transactionIncome = 0,
                            transactionExpenses = 0,
                            transactionNote = createTransUiState.note,
                            transactionTransfer = createTransUiState.transfer,
                            transactionDescription = createTransUiState.description,
                            transactionCreated = createTransUiState.date,
                            transactionMonth = createTransUiState.date.monthNumber.toLong() + 1,
                            transactionYear = createTransUiState.date.year.toLong(),
                            transactionCategory = createTransUiState.category,
                            transactionAccount = createTransUiState.account,
                            transactionSelectIndex = selectIndex.value,
                            transactionAccountFrom = createTransUiState.accountFrom,
                            transactionAccountTo = createTransUiState.accountTo
                        )
                    }
                }


                Napier.d(
                    message = "${createTransUiState.date.month.ordinal} and ${createTransUiState.date.month}",
                    tag = "month"
                )
                viewModel.insertTransaction(transaction)
                navigator.pop()
                viewModel.loadTransaction()
            },
            modifier = Modifier.weight(1f)
                .padding(end = create_transaction_padding_row)
                .height(buttonHeight_transaction_buttonNextAction),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(
                    when (transactionType) {
                        TransactionType.Expense -> MR.colors.createTrans_tab_expense
                        TransactionType.Income -> MR.colors.createTrans_tab_income
                        TransactionType.Transfer -> MR.colors.createTrans_tab_transfer
                    }
                )
            ),
            shape = RoundedCornerShape(20)
        ) {
            Text(
                text = MR.strings.createTrans_button_createTrans.desc()
                    .localized(),
                color = Color.White
            )
        }

        Button(
            onClick = { /* Handle image button click */ },
            modifier = Modifier.height(buttonHeight_transaction_buttonNextAction),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
            border = BorderStroke(
                thickness_transaction_borderStroke,
                Color.Black
            )
        ) {
            Text(
                text = MR.strings.createTrans_button_saveAndCreateAnother.desc()
                    .localized(), color = Color.Black
            )
        }
    }
}


enum class TransactionType {
    Expense,
    Income,
    Transfer
}
