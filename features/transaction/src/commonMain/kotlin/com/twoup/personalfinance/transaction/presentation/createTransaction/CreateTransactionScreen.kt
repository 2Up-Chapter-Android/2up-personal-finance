package com.twoup.personalfinance.transaction.presentation.createTransaction

import PersonalFinance.features.transaction.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.wallet.Wallet
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
import com.twoup.personalfinance.transaction.presentation.theme.paddingHorizontal_createTrans_chooseWallet_actionBar
import com.twoup.personalfinance.transaction.presentation.theme.paddingHorizontal_createTrans_chooseWallet_walletItem
import com.twoup.personalfinance.transaction.presentation.theme.paddingVertical_createTrans_chooseWallet_walletItem
import com.twoup.personalfinance.transaction.presentation.theme.textSize_createTransaction_chooseWallet_actionBar
import com.twoup.personalfinance.transaction.presentation.theme.textSize_createTransaction_chooseWallet_walletITem_name
import com.twoup.personalfinance.transaction.presentation.theme.textSize_transaction_textField
import com.twoup.personalfinance.transaction.presentation.theme.thickness_transaction_borderStroke
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        val createTransUiState = viewModel.createTransUiState.collectAsState()
        val getListWalletState = viewModel.getListWalletState.collectAsState()
        val selectedTabIndex = remember { mutableStateOf(0) }
        val tabList = listOf(
            MR.strings.createTrans_tab_income.desc().localized(),
            MR.strings.createTrans_tab_expense.desc().localized(),
            MR.strings.createTrans_tab_transfer.desc().localized()
        )
        val listWallet = remember { mutableStateOf(mutableListOf<Wallet>()) }
        val coroutineScopeCategory = rememberCoroutineScope()
        val bottomSheetVisible = remember { mutableStateOf(false) }
        val bottomSheetStateAccounts =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

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

        ModalBottomSheetLayout(
            sheetState = bottomSheetStateAccounts.apply {
                bottomSheetVisible.value = isVisible
            },
            sheetContent = {
                AccountsModalBottomSheetLayout()
//                CategoryModalBottomSheetLayout()
            },
            content = {
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
                            text = createTransUiState.value.date,
                            textLabel = MR.strings.createTrans_inputLabel_date.desc().localized(),
                            onTextChange = { viewModel.onDateChange(it) },
                            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                            bottomSheetState = bottomSheetStateAccounts,
                            coroutineScope = coroutineScopeCategory
                        )

                        LineTransInfor(
                            text = createTransUiState.value.amount,
                            textLabel = MR.strings.createTrans_inputLabel_amount.desc().localized(),
                            onTextChange = { viewModel.onAmountChange(it) },
                            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                            bottomSheetState = bottomSheetStateAccounts,
                            coroutineScope = coroutineScopeCategory
                        )

                        LineTransInfor(
                            text = createTransUiState.value.category,
                            textLabel = MR.strings.createTrans_inputLabel_category.desc()
                                .localized(),
                            onTextChange = { viewModel.onCategoryChange(it) },
                            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                            bottomSheetState = bottomSheetStateAccounts,
                            coroutineScope = coroutineScopeCategory
                        )

                        LineTransInfor(
                            text = createTransUiState.value.account?.name ?: "",
                            textLabel = MR.strings.createTrans_inputLabel_account.desc()
                                .localized(),
                            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                            readOnly = true,
                            textFieldModifier = Modifier.onFocusChanged {
                                viewModel.openCloseChooseWallet(it.hasFocus)
                            },
                            bottomSheetState = bottomSheetStateAccounts,
                            coroutineScope = coroutineScopeCategory
                        )

                        LineTransInfor(
                            text = createTransUiState.value.note,
                            textLabel = MR.strings.createTrans_inputLabel_note.desc().localized(),
                            onTextChange = { viewModel.onNoteChange(it) },
                            keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
                            bottomSheetState = bottomSheetStateAccounts,
                            coroutineScope = coroutineScopeCategory
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
                                onClick = { /* Handle create button click */ },
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
                        Column(
                            modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()
                                .background(color = colorResource(MR.colors.createTrans_chooseWallet_background))
                        ) {
                            Row(
                                modifier = Modifier.background(Color.Black).fillMaxWidth()
                                    .padding(horizontal = paddingHorizontal_createTrans_chooseWallet_actionBar),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    MR.strings.createTrans_inputLabel_account.desc().localized(),
                                    color = Color.White,
                                    fontSize = textSize_createTransaction_chooseWallet_actionBar
                                )
                                IconButton(onClick = { focusManager.clearFocus() }) {
                                    Icon(imageVector = Icons.Default.Clear, "", tint = Color.White)
                                }
                            }
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3)
                            ) {
                                items(listWallet.value.size) {
                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width = Dp(0.5f),
                                                color = colorResource(MR.colors.createTrans_chooseWallet_walletItem_border)
                                            )
                                            .background(Color.White)
                                            .padding(
                                                horizontal = paddingHorizontal_createTrans_chooseWallet_walletItem,
                                                vertical = paddingVertical_createTrans_chooseWallet_walletItem
                                            )
                                            .clickable(
                                                interactionSource = interactionSource,
                                                indication = null
                                            ) {
                                                viewModel.onAccountChange(listWallet.value[it])
                                                focusManager.clearFocus()
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = listWallet.value[it].name,
                                            color = Color.Black,
                                            fontSize = textSize_createTransaction_chooseWallet_walletITem_name,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun LineTransInfor(
        textLabel: String,
        text: String,
        onTextChange: (String) -> Unit = {},
        keyboardOption: KeyboardOptions,
        readOnly: Boolean = false,
        textFieldModifier: Modifier = Modifier,
        coroutineScope: CoroutineScope,
        bottomSheetState: ModalBottomSheetState,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                create_transaction_padding_row
            ).clickable(onClick = {
                coroutineScope.launch {
                    bottomSheetState.show()
                }
            })
        ) {
            Text(
                text = textLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(
                    start = create_transaction_padding_start_text,
                    end = create_transaction_padding_end_text
                ).weight(2f)
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