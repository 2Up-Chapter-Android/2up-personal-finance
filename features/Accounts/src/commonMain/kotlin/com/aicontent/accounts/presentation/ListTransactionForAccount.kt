package com.aicontent.accounts.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.accounts.theme.height_row_top_bar
import com.aicontent.accounts.theme.padding_end_text_daily_item
import com.aicontent.accounts.theme.padding_text_top_bar
import com.aicontent.accounts.theme.rounded_corner_shape
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil
import com.twoup.personalfinance.utils.DateTimeUtil.formatTimeForAccountFirst
import com.twoup.personalfinance.utils.DateTimeUtil.formatTimeForAccountLast
import com.twoup.personalfinance.utils.presentation.adjustFontSize
import io.github.aakira.napier.Napier

class ListTransactionForAccount(
    private val allTransaction: List<TransactionLocalModel>,
    private val account: AccountLocalModel
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { AccountListViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val transactionByMonth = viewModel.getAccountTransactions(
            viewModel.transactionByMonth.collectAsState().value,
            account
        )

        val listTransactions = viewModel.getAccountTransactions(allTransaction, account)
        val monthYear = viewModel.currentMonthYear

        LaunchedEffect(Unit) {
            viewModel.loadAccount()
            viewModel.filterTransactionByMonth(monthYear.value.month, monthYear.value.year)
        }

        Napier.d("list transaction ${listTransactions.size}", tag = "test list")
        Scaffold(
            topBar = {
                TopAppBarListAccount(
                    navigator, account, viewModel
                )
            },
            content = {
                Column(
                    modifier = Modifier.padding(bottom = 56.dp), // Adjust the bottom padding to match BottomAppBar height
                ) {
                    val firstTransaction = listTransactions.firstOrNull() ?: viewModel.getDefaultTransaction()
                    GroupTopBar(firstTransaction)
                    Divider(thickness = 0.5.dp, color = Color.LightGray)
                    ElementAccount(viewModel, transactionByMonth, listTransactions, account)
                    ListTransaction(
                        navigator, viewModel, transactionByMonth
                    )
                }
            }
        )
    }
}


@Composable
fun ElementAccount(
    viewModel: AccountListViewModel,
    listTransaction: List<TransactionLocalModel>,
    allTransaction: List<TransactionLocalModel>,
    account: AccountLocalModel,
) {
    viewModel.updateData(listTransaction, allTransaction, account)

    val income by viewModel.income.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    val total by viewModel.total.collectAsState()
    val totalAccount by viewModel.totalAccountValue.collectAsState()
    val colorText by viewModel.colorText.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ElementRow("Deposit", income, Color.Blue)
        ElementRow("Withdrawal", expenses, Color.Red)
        ElementRow("Total", total, colorText)
        ElementRow("Balance", totalAccount, colorText)
    }
}


@Composable
fun ElementRow(
    text: String,
    number: Long,
    color: Color
) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(number.toString(), fontSize = 12.sp, color = color)
    }
}

@Composable
fun GroupTopBar(transaction: TransactionLocalModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp).padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Statement", color = Color.Gray)
            Text(
                "${formatTimeForAccountFirst(transaction.transaction_created)} ~ ${
                    formatTimeForAccountLast(
                        transaction.transaction_created
                    )
                }"
            )
        }
        Row {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun ListTransaction(
    navigator: Navigator,
    viewModel: AccountListViewModel,
    listTransaction: List<TransactionLocalModel>
) {
    val distinctTransactions = listTransaction.distinctBy { it.transaction_created.date.dayOfMonth }

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
                            listTransaction.filter { it.transaction_created.date.dayOfMonth == dateDistinct.transaction_created.date.dayOfMonth }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            TitleTransaction(dateDistinct, transactions, viewModel)
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            DateTransactionsGroup(transactions, navigator)
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
    Divider(thickness = 0.5.dp, color = Color.LightGray)
}


@Composable
fun DateTransactionsGroup(transactions: List<TransactionLocalModel>, navigator: Navigator) {
    Column {
        transactions.forEach { transaction ->
            val itemTransactionScreen =
                rememberScreen(MainScreenSharedScreen.ItemTransaction(transaction))
            val isTransfer = transaction.transaction_transfer > 0

            ItemDailyScreen(
                transaction,
                onNoteClick = { navigator.push(itemTransactionScreen) },
                isTransfer = isTransfer
            )
        }
    }
}

@Composable
fun TitleTransaction(
    dateDistinct: TransactionLocalModel,
    transactions: List<TransactionLocalModel>,
    viewModel : AccountListViewModel
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
                text = DateTimeUtil.formatDateTransDays(dateDistinct.transaction_created),
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
                        text = DateTimeUtil.formatDateTransMonth(dateDistinct.transaction_created),
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
            }

            Text(
                text = DateTimeUtil.formatDateTrans(dateDistinct.transaction_created),
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
}
@Composable
fun TopAppBarListAccount(
    navigator: Navigator,
    account: AccountLocalModel,
    viewModel: AccountListViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = account.account_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navigator.pop()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxHeight() // Use fillMaxHeight to set the height
                    .background(
                        color = MaterialTheme.colors.surface,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MonthButton(Icons.Default.KeyboardArrowLeft) {
                    viewModel.decrementMonth()
                }

                BoldMonthText(
                    month = viewModel.getAbbreviatedMonth(viewModel.currentMonthYear.value.month),
                    year = viewModel.currentMonthYear.value.year
                )

                MonthButton(Icons.Default.KeyboardArrowRight) {
                    viewModel.incrementMonth()
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = AppBarDefaults.TopAppBarElevation
    )
}

@Composable
fun MonthButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    )
}

@Composable
fun BoldMonthText(month: String, year: Int) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(month)
        }
        append(".")
        append(year.toString())
    }

    Text(
        text = text,
        color = Color.Black, // Change the color as needed
        modifier = Modifier.padding(horizontal = padding_text_top_bar),
        fontSize = 16.sp // Change the font size as needed
    )
}