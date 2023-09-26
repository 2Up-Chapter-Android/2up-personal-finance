package com.aicontent.main.presentation.daily

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.theme.padding_end_text_daily_item
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DailyScreen(viewModel: DailyScreenViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val listTransaction = viewModel.transaction.value.sortedByDescending { it.created.date.dayOfMonth }
    val distinctTransactions = listTransaction.distinctBy { it.created.date.dayOfMonth }

    LaunchedEffect(navigator) {
        viewModel.loadNotes()
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
                            listTransaction.filter { it.created.date.dayOfMonth == dateDistinct.created.date.dayOfMonth }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            TitleTransaction(dateDistinct, transactions)

                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            DateTransactionsGroup(transactions, navigator)
                            Divider(thickness = 0.5.dp, color = Color.LightGray)

                        }
                    }
                    item {
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
fun DateTransactionsGroup(transactions: List<TransactionLocalModel>, navigator: Navigator) {
    Column {
        transactions.forEach { transaction ->
            val itemTransactionScreen =
                rememberScreen(MainScreenSharedScreen.ItemTransaction(transaction))

            if (transaction.transferBalance!! > 0) {
                ItemDailyTransferScreen(transaction) {
                    navigator.push(itemTransactionScreen)
                }
            } else if (transaction.income!! > 0 || transaction.expenses !!> 0) {
                ItemDailyScreen(transaction) {
                    navigator.push(itemTransactionScreen)
                }
            }
        }
    }
}

@Composable
fun TitleTransaction(
    dateDistinct: TransactionLocalModel,
    transactions: List<TransactionLocalModel>
) {
    val totalIncome = calculateTotalIncome(transactions)
    val totalExpenses = calculateTotalExpenses(transactions)
    val boxColor = Color(0xFF336699) // Replace "336699" with your desired hex RGB value

    Row(modifier = Modifier.padding(4.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = DateTimeUtil.formatDateTransDays(dateDistinct.created),
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
//                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = DateTimeUtil.formatDateTransMonth(dateDistinct.created),
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
            }

            Text(
                text = DateTimeUtil.formatDateTrans(dateDistinct.created),
                fontWeight = FontWeight.Thin,
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Bottom).padding(4.dp)
            )
        }

        Row(modifier = Modifier.weight(1f), Arrangement.SpaceBetween) {
            Text(
                text = buildString {
                    append(totalIncome)
                    append("đ")
                },
                color = Color.Blue
            )
            Text(
                text = buildString {
                    append(totalExpenses)
                    append("đ")
                },
                color = Color.Red,
                modifier = Modifier.padding(end = padding_end_text_daily_item)
            )
        }
    }
}

fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Int {
    return transactions.filter { it.income > 0 }.sumOf { it.income }.toInt()
}
fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Int {
    return transactions.filter { it.expenses > 0 }.sumOf { it.expenses }.toInt()
}