package com.twoup.personalfinance.transaction.presentation.createTransaction

import PersonalFinance.features.transaction.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.utils.data.fold
import com.twoup.personalfinance.transaction.presentation.theme.buttonHeight_transaction_buttonNextAction
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_end_text
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_row
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_padding_start_text
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_bottom
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_horizontal
import com.twoup.personalfinance.transaction.presentation.theme.create_transaction_spacer_padding_top
import com.twoup.personalfinance.transaction.presentation.theme.marginStart_createTrans_actionBar_tabName
import com.twoup.personalfinance.transaction.presentation.theme.textSize_transaction_textField
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

class CreateTransactionScreen : Screen {
    @Composable
    override fun Content() {
        CreateTransactionScreen()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CreateTransactionScreen() {
        val navigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val viewModel = rememberScreenModel { CreateTransViewModel() }
        val addNoteScreen = rememberScreen(MainScreenSharedScreen.MainScreen)
        val createTransUiState = viewModel.createTransUiState.collectAsState()
        val getListWalletState = viewModel.getListWalletState.collectAsState()
        val selectedTabIndex = remember { mutableStateOf(0) }
        val tabList = listOf(
            MR.strings.createTrans_tab_income.desc().localized(),
            MR.strings.createTrans_tab_expense.desc().localized(),
            MR.strings.createTrans_tab_transfer.desc().localized()
        )
        val listWallet = remember { mutableStateOf(mutableListOf<Wallet>()) }
        val categorys by viewModel.categorys.collectAsState(emptyList())
        val accounts by viewModel.accounts.collectAsState(emptyList())

        LaunchedEffect(navigator) {
            viewModel.loadTransaction()
        }

        LaunchedEffect(getListWalletState.value) {
            getListWalletState.value.fold(
                onSuccess = {
                    listWallet.value.clear()
                    listWallet.value.addAll(it.data)
                },
                onFailure = {

                },
                onLoading = {
                    it?.let {
                        listWallet.value.clear()
                        listWallet.value.addAll(it.data)
                    }
                }
            )
        }

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
                        text = createTransUiState.value.date.toString(),
                        textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                        onTextChange = { viewModel.onDateChange(createTransUiState.value.date) },
                        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
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
                                        account = createTransUiState.value.account
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

                AnimatedVisibility(visible = createTransUiState.value.isOpenChooseWallet) {
                    AccountBottomSheet(
                        focusManager = focusManager,
                        accounts = accounts,
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }
                AnimatedVisibility(visible = createTransUiState.value.isOpenChooseCategory) {
                    CategoryBottomSheet(
                        focusManager = focusManager,
                        categorys = categorys,
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }
//                AnimatedVisibility(visible = createTransUiState.value.isOpenChooseAmount) {
////                    AmountBottomSheet(
////                        focusManager = focusManager,
////                        viewModel = viewModel,
////                        interactionSource = interactionSource,
////                        onNumberClicked = {}
////                    )
//                    AmountBottomSheet(
//                        focusManager = focusManager,
//                        viewModel = viewModel,
//                        interactionSource = interactionSource,
//                        onNumberClicked = {}
//                    )
//                }

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
        onTextChange: (String) -> Unit = {},
        keyboardOption: KeyboardOptions,
        readOnly: Boolean = false,
        textFieldModifier: Modifier = Modifier,
//        viewModel: CreateTransViewModel,
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
}