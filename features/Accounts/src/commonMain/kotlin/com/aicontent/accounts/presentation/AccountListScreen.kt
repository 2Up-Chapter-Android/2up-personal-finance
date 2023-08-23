package com.aicontent.accounts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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

        AccountListScreen(accounts)
    }

}

@Composable
fun AccountListScreen(accounts: List<AccountLocalModel>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "My Accounts") })
        },
        content = {
            AccountList(accounts = accounts)
        }
    )
}

@Composable
fun AccountList(accounts: List<AccountLocalModel>) {
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
                    text = "0",
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
                    text = "0",
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
                    text = "0",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }

        LazyColumn {
            items(accounts) { account ->
                AccountItem(account) { }
            }
        }
    }
}


@Composable
fun AccountItem(account: AccountLocalModel, onItemClick: () -> Unit) {
    val balance = account.income?.minus(account.expense!!) ?: 0.0
    val balanceColor = if (balance > 0) Color.Blue else Color.Red

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = account.account_name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Balance: $$balance",
                style = MaterialTheme.typography.body1,
                color = balanceColor
            )
        }
    }
}