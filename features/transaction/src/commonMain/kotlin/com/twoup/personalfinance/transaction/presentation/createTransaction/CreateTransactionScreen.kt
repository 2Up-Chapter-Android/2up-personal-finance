package com.twoup.personalfinance.transaction.presentation.createTransaction

import PersonalFinance.features.transaction.MR
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.transaction.presentation.theme.buttonHeight_transaction_buttonNextAction
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_end_text
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_row
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_start_text
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_vertical
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_bottom
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_top
import com.twoup.personalfinance.transaction.presentation.theme.marginStart_createTrans_actionBar_tabName
import com.twoup.personalfinance.transaction.presentation.theme.textSize_transaction_textField
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.desc.desc

class CreateTransactionScreen : Screen {
    @Composable
    override fun Content() {
        CreateTransactionScreen()
    }


    @Composable
    fun CreateTransactionScreen() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { CreateTransViewModel() }
        val createTransUiState = viewModel.createTransUiState.collectAsState()
        val selectedTabIndex = remember { mutableStateOf(0) }
        val tabList = listOf(
            MR.strings.createTrans_tab_income.desc().localized(),
            MR.strings.createTrans_tab_expense.desc().localized(),
            MR.strings.createTrans_tab_transfer.desc().localized()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                contentColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.surface,
                divider = { /*remove underline*/ },
                indicator = { /*remove indicator*/ }
            ) {
                tabList.forEachIndexed { index, tabName ->
                    tabLayoutTrans(
                        index = index,
                        value = tabName,
                        selectedTabIndex = selectedTabIndex
                    )
                }
            }
            LineTransInfor(
                text = when (selectedTabIndex.value) {
                    0 -> "INCOME"
                    1 -> "EXPENSE"
                    else -> "TRANSFER"
                },
                textLabel = "",
                onTextChange = {
                    viewModel.onTypeChange(it)
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = createTransUiState.value.date.toString(),
                textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                onTextChange = {
                    viewModel.onDateChange(it.toLong())
                },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = createTransUiState.value.amount.toString(),//convert int to string
                textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                onTextChange = { viewModel.onAmountChange(it.toInt()) },//convert string to int
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = createTransUiState.value.category,
                textLabel = MR.strings.createTrans_inputLabel_category.desc().localized(),
                onTextChange = { viewModel.onCategoryChange(it) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
            )

            LineTransInfor(
                text = createTransUiState.value.account,
                textLabel = MR.strings.createTrans_inputLabel_account.desc().localized(),
                onTextChange = { viewModel.onAccountChange(it) },
                keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
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
                    .background(colorResource(MR.colors.color_line_break)),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = create_transaction_padding_horizontal)
            ) {
                Button(
                    onClick = {
                        viewModel.createTransaction(createTransUiState.value.typeTrans)
                    },
                    enabled = createTransUiState.value.enableCreateTransButton,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = create_transaction_padding_row)
                        .height(buttonHeight_transaction_buttonNextAction),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = colorResource(MR.colors.color_tab_expense)),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(
                        text = MR.strings.createTrans_button_createTrans.desc().localized(),
                        color = Color.White
                    )
                }
                //Create this transaction and continue create another transaction
                Button(
                    onClick = { /* Handle image button click */ },
                    modifier = Modifier
                        .height(buttonHeight_transaction_buttonNextAction),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
                    border = BorderStroke(thickness_transaction_borderStroke, Color.Black),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(
                        text = MR.strings.createTrans_button_saveAndCreateAnother.desc()
                            .localized(), color = Color.Black
                    )

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
                onClick = { selectedTabIndex.value = index },
                modifier = Modifier.padding(vertical = create_transaction_padding_row),
                shape = RoundedCornerShape(20),
                border = if (selectedTabIndex.value == index) {
                    when (selectedTabIndex.value) {
                        0 -> BorderStroke(thickness_transaction_borderStroke, Color.Blue)
                        1 -> BorderStroke(
                            thickness_transaction_borderStroke,
                            colorResource(MR.colors.color_tab_expense)
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
                            contentColor = colorResource(MR.colors.color_tab_expense),
                            backgroundColor = Color.White
                        )

                        else -> ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black, backgroundColor = Color.White
                        )
                    }
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Gray,
                        backgroundColor = colorResource(MR.colors.color_tab_unselected)
                    )
                }
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun LineTransInfor(
        textLabel: String,
        text: String,
        onTextChange: (String) -> Unit,
        keyboardOption: KeyboardOptions,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                create_transaction_padding_row
            )
        ) {
            Text(
                text = textLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = create_transaction_padding_start_text,
                        end = create_transaction_padding_end_text
                    )
                    .weight(2f)
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = create_transaction_padding_end_text)
                    .weight(8f),
                value = text,
                onValueChange = { onTextChange(it) },
                textStyle = TextStyle(
                    fontSize = textSize_transaction_textField
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.textFieldColors(

                    backgroundColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray
                )
            )
        }
    }
}