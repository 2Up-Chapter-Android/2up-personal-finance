package com.aicontent.main.presentation.daily

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.MainScreenViewModel
import com.aicontent.main.theme.padding_end_text_daily_item
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil
import com.twoup.personalfinance.utils.presentation.adjustFontSize

@Composable
fun DailyScreen(viewModel: DailyScreenViewModel, transactionByMonth: List<TransactionLocalModel>, mainScreenViewModel: MainScreenViewModel ) {
    val navigator = LocalNavigator.currentOrThrow
    val listTransaction = transactionByMonth.sortedByDescending { it.transactionCreated }
    val distinctTransactions = listTransaction.distinctBy { it.transactionCreated.date.dayOfMonth }

    LaunchedEffect(Unit) {
        viewModel.loadTransaction()
        viewModel.filterTransactionByMonth(mainScreenViewModel.currentMonthYear.value.month, mainScreenViewModel.currentMonthYear.value.year)

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (listTransaction.isNotEmpty()) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                LazyColumn {
                    items(distinctTransactions) { dateDistinct ->
                        val transactions =
                            listTransaction.filter { it.transactionCreated.date.dayOfMonth == dateDistinct.transactionCreated.date.dayOfMonth }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            TitleTransaction(dateDistinct, transactions, viewModel)
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            DateTransactionsGroup(transactions, navigator, viewModel)
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        } else {
            Text(
                text = "No transactions available",
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DateTransactionsGroup(
    transactions: List<TransactionLocalModel>,
    navigator: Navigator,
    viewModel: DailyScreenViewModel
) {
    Column {
        transactions.forEach { transaction ->
            val itemTransactionScreen =
                rememberScreen(MainScreenSharedScreen.ItemTransaction(transaction))
            val isTransfer = transaction.transactionTransfer > 0

            ItemDailyScreen(
                transaction,
                onNoteClick = { navigator.push(itemTransactionScreen) },
                isTransfer = isTransfer,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun TitleTransaction(
    dateDistinct: TransactionLocalModel,
    transactions: List<TransactionLocalModel>,
    viewModel: DailyScreenViewModel
) {
    val totalIncome = viewModel.calculateTotalIncome(transactions)
    val totalExpenses = viewModel.calculateTotalExpenses(transactions)
    val boxColor = Color(0xFF336699) // Replace "336699" with your desired hex RGB value

    Row(
        modifier = Modifier.padding(4.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1.2f)) {
            Text(
                text = DateTimeUtil.formatDateTransDays(dateDistinct.transactionCreated),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Bottom)
                    .padding(start = padding_end_text_daily_item)
            )

            Card(
                modifier = Modifier
                    .height(24.dp)
                    .width(36.dp)
                    .align(Alignment.Bottom)
                    .padding(bottom = 4.dp, start = 2.dp),
                shape = RoundedCornerShape(12.dp), // Adjust the corner radius as needed
                backgroundColor = boxColor
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = DateTimeUtil.formatDateTransMonth(dateDistinct.transactionCreated),
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
            }

            Text(
                text = DateTimeUtil.formatDateTrans(dateDistinct.transactionCreated),
                fontWeight = FontWeight.Thin,
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Bottom).padding(4.dp)
            )
        }
        Text(
            text = buildString {
                append(totalIncome)
                append(" đ")
            },
            color = Color.Blue,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontSize = adjustFontSize(totalIncome.toString()).sp
        )

        Text(
            text = buildString {
                append(totalExpenses)
                append(" đ")
            },
            color = Color.Red,
            modifier = Modifier.padding(end = padding_end_text_daily_item).weight(1f),
            textAlign = TextAlign.End,
            fontSize = adjustFontSize(totalExpenses.toString()).sp
        )
    }
}

