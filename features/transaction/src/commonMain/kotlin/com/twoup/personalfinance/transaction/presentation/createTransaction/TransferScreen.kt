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
import androidx.compose.runtime.collectAsState
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
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_bottom
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_top
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier

@Composable
fun TransferScreen(
    viewModel: CreateTransViewModel,
    openDialog: MutableState<Boolean>,
    selectIndex: MutableState<Int>
) {
    val navigator = LocalNavigator.currentOrThrow
    val createTransUiState = viewModel.createTransUiState.collectAsState()
    val accounts = viewModel.accounts.value

    LaunchedEffect(navigator){
        viewModel.loadTransaction()
    }

    CompositionLocalProvider(
        LocalTextInputService provides null
    ) {
        LineTransInformation(
            text = DateTimeUtil.formatNoteDate(createTransUiState.value.date),
            textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
            onTextChange = { viewModel.onDateChange(createTransUiState.value.date) },
            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            readOnly = true,
            textFieldModifier = Modifier.onFocusChanged {
                viewModel.openCloseDatePicker(it.hasFocus)
                openDialog.value = it.hasFocus
            }
        )

        LineTransInformation(
            text = createTransUiState.value.accountFrom,
            textLabel = MR.strings.createTrans_inputLabel_from.desc().localized(),
            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            readOnly = true,
            textFieldModifier = Modifier.onFocusChanged {
                viewModel.openCloseChooseCategoryAccountFrom(it.hasFocus)
            }
        )

        LineTransInformation(
            text = createTransUiState.value.accountTo,
            textLabel = MR.strings.createTrans_inputLabel_to.desc().localized(),
            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            readOnly = true,
            textFieldModifier = Modifier.onFocusChanged {
                viewModel.openCloseChooseCategoryAccountTo(it.hasFocus)
            }
        )
    }
    LineTransInformation(
        text = createTransUiState.value.transferBalance.toString(),
        textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
        keyboardOption = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        onTextChange = { viewModel.onTransferChange(it) },
    )

    LineTransInformation(
        text = createTransUiState.value.note,
        textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
        onTextChange = { viewModel.onNoteChange(it) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
    )

    LineTransInformation(
        text = createTransUiState.value.description,
        textLabel = MR.strings.createTrans_inputLabel_description.desc().localized(),
        onTextChange = { viewModel.onNoteChange(it) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = false
    )

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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(create_transaction_padding_horizontal)
    ) {
        //Create this transaction and back to dashboard screen
        Button(
            onClick = {
                viewModel.insertTransaction(
                    TransactionLocalModel(
                        transaction_id = createTransUiState.value.id,
                        transaction_income = 0,
                        transaction_expenses = 0,
                        transaction_transfer = createTransUiState.value.transferBalance,
                        transaction_description = createTransUiState.value.description,
                        transaction_note = createTransUiState.value.note,
                        transaction_created = createTransUiState.value.date,
                        transaction_category = createTransUiState.value.category,
                        transaction_account = createTransUiState.value.account,
                        transaction_selectIndex = selectIndex.value,
                        transaction_accountFrom = createTransUiState.value.accountFrom,
                        transaction_accountTo = createTransUiState.value.accountTo
                    )
                )
                Napier.d(
                    "the number selected in transfer is  ${selectIndex.value}",
                    tag = "selectedTabIndex"
                )
                navigator.pop()
                viewModel.loadTransaction()

            },
            modifier = Modifier.weight(1f)
                .padding(end = create_transaction_padding_row)
                .height(buttonHeight_transaction_buttonNextAction),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(
                    MR.colors.createTrans_tab_transfer
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