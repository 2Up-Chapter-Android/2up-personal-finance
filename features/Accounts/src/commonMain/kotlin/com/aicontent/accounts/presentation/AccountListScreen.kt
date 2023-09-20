package com.aicontent.accounts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import cafe.adriel.voyager.core.screen.Screen
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel

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
                title = { Text(text = "My Accounts") },
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
    var transactions = viewModel.transactions.value
    var accountUiState = viewModel.accountUiState
    var totalAsset by remember { mutableStateOf(0) }
    var totalLiabilities by remember { mutableStateOf(0) }

    val asset = viewModel.transactions.value
    totalAsset = asset.filter { it.income > 0 }.sumOf { it.income }.toInt()
    totalLiabilities = asset.filter { it.expenses > 0 }.sumOf { it.expenses }.toInt()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Asset", fontSize = 14.sp)
                Text(
                    text = totalAsset.toString(),
                    color = Color.Blue,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Liabilities", fontSize = 14.sp)
                Text(
                    text = totalLiabilities.toString(),
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Total", fontSize = 14.sp)
                Text(
                    text = "${totalAsset - totalLiabilities}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)

        LazyColumn {
            items(accounts) { account ->
                val selectedTransactions =
                    transactions.filter { transaction -> transaction.account == account.account_name }
                val totalIncome = selectedTransactions.sumOf { transaction -> transaction.income }
                val totalExpense = selectedTransactions.sumOf { transactions -> transactions.expenses }

                val balance = totalIncome - totalExpense

                AccountItem(account, balance.toInt(), {})
            }
        }
    }
}

@Composable
fun AccountItem(account: AccountLocalModel, balance: Int, onItemClick: () -> Unit) {
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
            verticalAlignment = Alignment.CenterVertically,
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
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                text = account.account_name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
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
    }
}