package com.aicontent.main.presentation.daily

//import PersonalFinance.features.Main.MR
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aicontent.main.theme.paddingHorizontal_createTrans_chooseWallet_actionBar
import com.aicontent.main.theme.paddingHorizontal_createTrans_chooseWallet_walletItem
import com.aicontent.main.theme.paddingVertical_createTrans_chooseWallet_walletItem
import com.aicontent.main.theme.textSize_createTransaction_chooseWallet_actionBar
import com.aicontent.main.theme.textSize_createTransaction_chooseWallet_walletITem_name
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel

//import dev.icerock.moko.resources.compose.localized
//import dev.icerock.moko.resources.desc.desc

@Composable
fun AccountBottomSheet(
    accounts: List<AccountLocalModel>,
    viewModel: DailyScreenViewModel,
    interactionSource: MutableInteractionSource
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()
//            .background(color = colorResource(MR.colors.createTrans_chooseWallet_background))
            .background(color = Color(0xf4f4f4))
    ) {
        Row(
            modifier = Modifier.background(Color.Black).fillMaxWidth()
                .padding(horizontal = paddingHorizontal_createTrans_chooseWallet_actionBar),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                MR.strings.createTrans_inputLabel_account.desc().localized(),
                text = "Account",
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
            items(accounts) { account ->
                Box(
                    modifier = Modifier
                        .border(
                            width = Dp(0.5f),
//                            color = colorResource(MR.colors.createTrans_chooseWallet_walletItem_border)
                            color = Color(0xCCCCCC)
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
                            viewModel.updateShowSaveButton()

                            val uiState = viewModel.transactionUiState.value

                            with(uiState) {
                                if (isOpenChooseAccountTo) {
                                    viewModel.onAccountToChange(account.account_name)
                                }

                                if (isOpenChooseAccountFrom) {
                                    viewModel.onAccountFromChange(account.account_name)
                                }

                                if (isOpenChooseWallet) {
                                    viewModel.onAccountChange(account.account_name)
                                }
                            }
                            focusManager.clearFocus()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = account.account_name,
                        color = Color.Black,
                        fontSize = textSize_createTransaction_chooseWallet_walletITem_name,
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryBottomSheet(
    category: List<CategoryLocalModel>,
    viewModel: DailyScreenViewModel,
    interactionSource: MutableInteractionSource
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()
//            .background(color = colorResource(MR.colors.createTrans_chooseWallet_background))
            .background(color = Color(0xf4f4f4))

    ) {
        Row(
            modifier = Modifier.background(Color.Black).fillMaxWidth()
                .padding(horizontal = paddingHorizontal_createTrans_chooseWallet_actionBar),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                MR.strings.createTrans_inputLabel_category.desc().localized(),
                text = "Category",
                color = Color.White,
                fontSize = textSize_createTransaction_chooseWallet_actionBar
            )
            IconButton(onClick = { focusManager.clearFocus() }) {
                Icon(imageVector = Icons.Default.Clear, "", tint = Color.White)
            }
        }
        Row(Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                items(category) { category ->
                    Box(
                        modifier = Modifier
                            .border(
                                width = Dp(0.5f),
//                                color = colorResource(MR.colors.createTrans_chooseWallet_walletItem_border)
                                color = Color(0xCCCCCC)
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
                            viewModel.updateShowSaveButton()
                            viewModel.onCategoryChange(category.category_name)
                            focusManager.clearFocus()
                        },
                    contentAlignment = Alignment.TopStart
                    ) {
                    Text(
                        text = category.category_name,
                        color = Color.Black,
                        fontSize = textSize_createTransaction_chooseWallet_walletITem_name,
                    )
                }
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight().background(Color.Gray).padding(0.25.dp))
        }
    }
}


@Composable
fun AmountBottomSheet(
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()
//            .background(color = colorResource(MR.colors.createTrans_chooseWallet_background))
            .background(color = Color(0xf4f4f4))

    ) {
        Row(
            modifier = Modifier.background(Color.Black).fillMaxWidth()
                .padding(horizontal = paddingHorizontal_createTrans_chooseWallet_actionBar),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                MR.strings.createTrans_inputLabel_amount.desc().localized(),
                text = "Amount",
                color = Color.White,
                fontSize = textSize_createTransaction_chooseWallet_actionBar
            )
            IconButton(onClick = { focusManager.clearFocus() }) {
                Icon(imageVector = Icons.Default.Clear, "", tint = Color.White)
            }
        }

        NumericKeypad(
            onNumberClicked = { number ->
                inputText += number.toString()
            },
            onSpecialButtonClicked = { specialButton ->
                when (specialButton) {
                    SpecialButton.DELETE -> {
                        if (inputText.isNotEmpty()) {
                            inputText = inputText.dropLast(1)
                        }
                    }

                    SpecialButton.MINUS -> {
                        inputText += "-"
                    }

                    SpecialButton.CALCULATE -> {
                        // Perform calculation logic here
                    }

                    SpecialButton.EQUALS -> {
                        // Perform equals logic here
                    }
                }
            },
            interactionSource = interactionSource
        )
        Text(text = inputText, modifier = Modifier.padding(16.dp))

    }
}

@Composable
fun NumericKeypad(
    onNumberClicked: (Int) -> Unit,
    onSpecialButtonClicked: (SpecialButton) -> Unit,
    interactionSource: MutableInteractionSource
) {
    val numbers = remember { listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0) }
    val rows = numbers.chunked(3)

    Row(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize()
        ) {
            rows.forEachIndexed { rowIndex, rowNumbers ->
                items(rowNumbers) { number ->
                    NumericButton(
                        interactionSource,
                        number = number,
                        onClick = { onNumberClicked(number) }
                    )
                }
                item {
                    val specialButton = when (rowIndex) {
                        0 -> SpecialButton.DELETE
                        1 -> SpecialButton.MINUS
                        2 -> SpecialButton.CALCULATE
                        3 -> SpecialButton.EQUALS
                        else -> null
                    }
                    specialButton?.let {
                        NumericButton(
                            interactionSource,
                            specialButton = it,
                            onClick = { onSpecialButtonClicked(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NumericButton(
    interactionSource: MutableInteractionSource,
    number: Int? = null,
    specialButton: SpecialButton? = null,
    onClick: () -> Unit
) {
//    val borderColor = colorResource(MR.colors.createTrans_chooseWallet_walletItem_border)
    val borderColor = Color(0xCCCCCC)
    val paddingHorizontal = paddingHorizontal_createTrans_chooseWallet_walletItem
    val paddingVertical = paddingVertical_createTrans_chooseWallet_walletItem

    Box(
        modifier = Modifier
            .border(width = Dp(0.5f), color = borderColor)
            .background(Color.White)
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick // Pass the onClick callback here
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when {
                number != null -> number.toString()
                specialButton != null -> when (specialButton) {
                    SpecialButton.DELETE -> "X"
                    SpecialButton.MINUS -> "-"
                    SpecialButton.CALCULATE -> "Cal"
                    SpecialButton.EQUALS -> "="
                }

                else -> ""
            },
            style = MaterialTheme.typography.h5,
        )
    }
}

enum class SpecialButton {
    DELETE,
    MINUS,
    CALCULATE,
    EQUALS
}