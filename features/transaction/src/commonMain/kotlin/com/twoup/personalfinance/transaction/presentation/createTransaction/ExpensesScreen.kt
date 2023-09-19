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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
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

@Composable
fun ExpensesScreen(
    viewModel : CreateTransViewModel,
    navigator : Navigator,
    openDialog : MutableState<Boolean>,
    selectIndex : Int
) {
    val createTransUiState = viewModel.createTransUiState.collectAsState()

    LineTransInfor(
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

    LineTransInfor(
        text = createTransUiState.value.account,
        textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        readOnly = true,
        textFieldModifier = Modifier.onFocusChanged {
            viewModel.openCloseChooseWallet(it.hasFocus)
        }
    )

    LineTransInfor(
        text = createTransUiState.value.category,
        textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        readOnly = true,
        textFieldModifier = Modifier.onFocusChanged {
            viewModel.openCloseChooseCategory(it.hasFocus)
        }
    )

    LineTransInfor(
        text = createTransUiState.value.amount.toString(),
        textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        onTextChange = { viewModel.onAmountChange(it) },
    )

    LineTransInfor(
        text = createTransUiState.value.note,
        textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
        onTextChange = { viewModel.onNoteChange(it) },
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
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
                        amount = createTransUiState.value.amount,
                        description = createTransUiState.value.note,
                        created = createTransUiState.value.date,
                        category = createTransUiState.value.category,
                        account = createTransUiState.value.account,
                        selectIndex = selectIndex.toString()
                    )
                )
                navigator.pop()
            },
            modifier = Modifier.weight(1f)
                .padding(end = create_transaction_padding_row)
                .height(buttonHeight_transaction_buttonNextAction),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(
                    MR.colors.createTrans_tab_expense
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
        //Create this transaction and continue create another transaction
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
