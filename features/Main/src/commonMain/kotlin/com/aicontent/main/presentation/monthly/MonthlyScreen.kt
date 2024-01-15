package com.aicontent.main.presentation.monthly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.MainScreenViewModel
import com.aicontent.main.theme.font_size_text_item_category
import com.aicontent.main.theme.font_size_text_item_month
import com.aicontent.main.theme.padding_end_text_daily_item
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.utils.DateTimeUtil.formatFirstAndLastDayOfMonthRange
import com.twoup.personalfinance.utils.DateTimeUtil.formatTimeForTheFirstOfWeek
import com.twoup.personalfinance.utils.DateTimeUtil.getFirstAndLastDayOfMonth
import com.twoup.personalfinance.utils.DateTimeUtil.getWeeksInMonth
import com.twoup.personalfinance.utils.presentation.getAbbreviatedMonth

val VeryLightGray = Color(0xFFF5F5F5) // You can adjust the color code as needed

@Composable
fun MonthlyScreen(
    viewModel: MonthlyScreenViewModel,
    transactionByYear: List<TransactionLocalModel>?,
    viewModelMain : MainScreenViewModel
) {
    // Check if transactionByYear is null or empty
    if (transactionByYear.isNullOrEmpty()) {
        Text(
            text = "No transactions available",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        return
    }

    val navigator = LocalNavigator.currentOrThrow
    val listTransaction = transactionByYear.sortedByDescending { it.transactionCreated }
    val distinctTransactions = listTransaction.distinctBy { it.transactionCreated.monthNumber }
    // Find the maximum transaction creation date
    val maxTransactionDate =
        listTransaction.maxByOrNull { it.transactionCreated }?.transactionCreated
    // Filter transactions that occurred in the latest month
    val latestMonthTransactions =
        listTransaction.filter { it.transactionCreated == maxTransactionDate }
    val listMonth: List<Int> = (1..latestMonthTransactions.last().transactionCreated.monthNumber).toList().reversed()

    LaunchedEffect(Unit) {
        viewModel.loadTransaction()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = listTransaction.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Divider(thickness = 0.5.dp, color = Color.LightGray)

            LazyColumn {
                val year = distinctTransactions.reversed().last().transactionCreated.date.year
                items(listMonth) { month ->
                    val (firstDayInWeek, lastDayInWeek) = getFirstAndLastDayOfMonth(year, month)
                    val isListWeekVisible = remember(month) { mutableStateOf(false) }

                    val transactionsForMonth = listTransaction.filter {
                        it.transactionCreated.monthNumber == month
                    }

                    Box(modifier = Modifier.clickable {
                        isListWeekVisible.value = !isListWeekVisible.value
                    }) {
                        ItemMonthlyScreen(
                            isThisMonthly = false,
                            viewModel = viewModel,
                            textMonth = month,
                            transactions = transactionsForMonth,
                        )
                    }
                    Divider(thickness = 0.5.dp, color = Color.LightGray)

                    val listWeekInMonth = getWeeksInMonth(firstDayInWeek, lastDayInWeek, 7)

                    if (isListWeekVisible.value) {
                        listWeekInMonth.reversed().forEach { week ->
                            val transactionsForWeek =
                                listTransaction.filter { transaction ->
                                    transaction.transactionCreated in week.first()..week.last()
                                }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(50.dp)
                                    .background(VeryLightGray).clickable {
                                        viewModelMain.currentMonthYear.value.month = month
                                        viewModelMain.selectedTabIndex.value = 0
                                    }
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = padding_end_text_daily_item)
                                        .width(80.dp).weight(1f),
                                    text = formatTimeForTheFirstOfWeek(week),
                                    fontSize = font_size_text_item_category,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                                DisplayTotals(
                                    transactionsForWeek,
                                    viewModel,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemMonthlyScreen(
    isThisMonthly: Boolean = true,
    viewModel: MonthlyScreenViewModel,
    transactions: List<TransactionLocalModel>,
    textMonth: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp).height(50.dp)
    ) {
        if (!isThisMonthly) {
            Text(
                modifier = Modifier.padding(start = padding_end_text_daily_item).width(80.dp)
                    .weight(1f),
                text = getAbbreviatedMonth(textMonth),
                fontSize = font_size_text_item_month,
                color = Color.Gray,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(start = padding_end_text_daily_item)
                        .width(80.dp),
                    text = getAbbreviatedMonth(textMonth),
                    fontSize = font_size_text_item_month,
                    color = Color.Gray,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(start = padding_end_text_daily_item)
                        .width(80.dp),
                    text = formatFirstAndLastDayOfMonthRange(
                        transactions.first().transactionCreated.year,
                        month = textMonth
                    ),
                    fontSize = font_size_text_item_category,
                    color = Color.Gray,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        DisplayTotals(transactions, viewModel, modifier = Modifier.weight(1f))
    }
}

@Composable
fun DisplayTotals(
    transactions: List<TransactionLocalModel>,
    viewModel: MonthlyScreenViewModel,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(end = padding_end_text_daily_item).weight(1f),
            text = "${viewModel.calculateTotalIncome(transactions)}đ",
            color = Constants.blueColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 10.sp
        )
        Column {
            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item).weight(1f)
                    .align(Alignment.End),
                text = "${viewModel.calculateTotalExpenses(transactions)}đ",
                color = Constants.redColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp
            )
            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item).weight(1f)
                    .align(Alignment.End),
                text = "${viewModel.calculateTotalLast(transactions)}đ",
                color = Constants.grayColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp
            )
        }
    }
}

object Constants {
    val blueColor = Color.Blue
    val redColor = Color.Red
    val grayColor = Color.Gray

    // Add other constants here
}