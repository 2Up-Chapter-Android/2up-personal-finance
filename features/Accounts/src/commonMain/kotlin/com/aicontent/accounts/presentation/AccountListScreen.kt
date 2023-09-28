package com.aicontent.accounts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.AccountSharedScreen
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import io.github.aakira.napier.Napier

val VeryLightGray = Color(0xFFF5F5F5) // You can adjust the color code as needed

class AccountListScreen() : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { AccountListViewModel() }
        val accounts by viewModel.accounts.collectAsState(emptyList())

        AccountListScreen(accounts, viewModel)
    }

}

@Composable
fun AccountListScreen(accounts: List<AccountLocalModel>, viewModel: AccountListViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Accounts", fontSize = 16.sp) },
                actions = {
                    Row {
                        IconButton(onClick = { /* Handle first icon click */ }) {
                            Icon(Icons.Filled.Star, contentDescription = "First Icon")
                        }
                        IconButton(onClick = { /* Handle second icon click */ }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "Second Icon")
                        }
                    }
                }
            )
        },
        content = {
            AccountList(accounts = accounts, viewModel = viewModel)
        }
    )
}

@Composable
fun AccountList(accounts: List<AccountLocalModel>, viewModel: AccountListViewModel) {
    val transactions = viewModel.transactions.value
    val totalAsset =
        transactions.filter { it.transaction_income > 0 }.sumOf { it.transaction_income }
    val totalLiabilities =
        transactions.filter { it.transaction_expenses > 0 }.sumOf { it.transaction_expenses }
    val totalBalance = totalAsset - totalLiabilities
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TotalRow(totalAsset, totalLiabilities, totalBalance)

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color.LightGray
        )

        fun getSelectedTransactions(account: AccountLocalModel): List<TransactionLocalModel> {
            return transactions.filter { transaction -> transaction.transaction_account == account.account_name }
        }

        fun getAccountTransactions(account: AccountLocalModel): List<TransactionLocalModel> {
            return transactions.filter {
                it.transaction_account == account.account_name
                        || it.transaction_accountFrom == account.account_name
                        || it.transaction_accountTo == account.account_name
            }
        }

// In your LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(accounts) { account ->
                val selectedTransactions = getSelectedTransactions(account)
                val balance =
                    calculateBalance(selectedTransactions, transactions, account, accounts)
                val listTransactionForAccount = rememberScreen(
                    AccountSharedScreen.ListTransactionForAccountScreen(
                        getAccountTransactions(account), account
                    )
                )
                AccountItem(account, balance) {
                    navigator.push(listTransactionForAccount)
                }
            }
        }

    }
}

@Composable
fun TotalRow(asset: Long, liabilities: Long, totalBalance: Long) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))

        TotalColumn("Asset", asset, Color.Blue)
        Spacer(modifier = Modifier.padding(8.dp))

        TotalColumn("Liabilities", liabilities, Color.Red)
        Spacer(modifier = Modifier.padding(8.dp))

        TotalColumn("Total", totalBalance, Color.Black)
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun TotalColumn(title: String, value: Long, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(title, fontSize = 14.sp)
        Text(
            text = value.toString(),
            color = color,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun calculateBalance(
    selectedTransactions: List<TransactionLocalModel>,
    allTransactions: List<TransactionLocalModel>,
    account: AccountLocalModel,
    allAccounts: List<AccountLocalModel>
): Long {
    // Calculate the total income, expenses, transfers from, and transfers to in a single pass
    var totalIncome = 0L
    var totalExpense = 0L
    var totalTransferFrom = 0L
    var totalTransferTo = 0L

    for (transaction in selectedTransactions) {
        totalIncome += transaction.transaction_income
        totalExpense += transaction.transaction_expenses
    }
    for (transaction in allTransactions.filter { it.transaction_transfer > 0 }) {
        if (transaction.transaction_accountFrom == account.account_name) {
            totalTransferFrom += transaction.transaction_transfer
        }

        if (transaction.transaction_accountTo == account.account_name) {
            totalTransferTo += transaction.transaction_transfer
        }
        Napier.d("transaction = $transaction", tag = "total")
        Napier.d("account = $account", tag = "total")

    }
    Napier.d("totalTransferFrom = $totalTransferFrom", tag = "total")
    Napier.d("totalTransferTo = $totalTransferTo", tag = "total")

    return totalIncome - totalExpense - totalTransferFrom + totalTransferTo
}


@Composable
fun AccountItem(account: AccountLocalModel, balance: Long, onItemClick: () -> Unit) {
    val balanceColor = if (balance > 0) Color.Blue else Color.Red

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = account.account_name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.weight(1f).padding(8.dp)
            )

            Text(
                text = "$balance",
                style = MaterialTheme.typography.body1,
                color = balanceColor,
                modifier = Modifier.weight(1f).padding(8.dp),
                textAlign = TextAlign.End
            )
        }
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(onClick = onItemClick)
                .background(VeryLightGray),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = account.account_name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.weight(1f).padding(8.dp),
            )

            Text(
                text = "$balance",
                style = MaterialTheme.typography.body1,
                color = balanceColor,
                modifier = Modifier.weight(1f).padding(8.dp),
                textAlign = TextAlign.End
            )
        }
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
    }
}