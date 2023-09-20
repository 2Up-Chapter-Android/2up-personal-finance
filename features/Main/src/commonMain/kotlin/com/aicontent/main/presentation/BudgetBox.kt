package com.aicontent.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.aicontent.main.theme.font_size_text_item_budge
import com.aicontent.main.theme.padding_budge_box
import com.aicontent.main.theme.padding_budge_item
import com.twoup.personalfinance.domain.model.transaction.TransactionType

@Composable
fun BudgetBox(viewModel: MainScreenViewModel) {
    var totalIncome by remember { mutableStateOf(0) }
    var totalExpenses by remember { mutableStateOf(0) }

    val incomes = viewModel.transaction.value
    totalIncome = incomes.filter { it.income > 0 }.sumOf { it.income }.toInt()
    totalExpenses = incomes.filter { it.expenses > 0 }.sumOf { it.expenses }.toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(padding_budge_box),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.padding(padding_budge_item))
        BudgetItem("Income", totalIncome, Color.Blue)
        Spacer(modifier = Modifier.padding(padding_budge_item))
        BudgetItem("Expenses", totalExpenses, Color.Red)
        Spacer(modifier = Modifier.padding(padding_budge_item))
        BudgetItem("Total", totalIncome - totalExpenses, Color.Black)
        Spacer(modifier = Modifier.padding(padding_budge_item))
    }
}

@Composable
fun BudgetItem(name: String, amount: Int, textColor: Color) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(name, fontSize = font_size_text_item_budge)
        Text(
            text = "$amount",
            color = textColor,
            fontSize = font_size_text_item_budge,
            modifier = Modifier.padding(top = padding_budge_item)
        )
    }
}
