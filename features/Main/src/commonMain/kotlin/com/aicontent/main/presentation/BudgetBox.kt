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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.aicontent.main.theme.font_size_text_item_budge
import com.aicontent.main.theme.padding_budge_box
import com.aicontent.main.theme.padding_budge_item
import com.twoup.personalfinance.utils.presentation.adjustFontSize

@Composable
fun BudgetBox(viewModel: MainScreenViewModel) {
    val transaction = viewModel.transaction.value
    val totalIncome = viewModel.calculateTotalIncome(transaction)
    val totalExpenses = viewModel.calculateTotalExpenses(transaction)

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
fun BudgetItem(name: String, amount: Long, textColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(name, fontSize = font_size_text_item_budge)
        Text(
            text = "$amount",
            color = textColor,
            fontSize = adjustFontSize(amount.toString()).sp
        )
    }
}
