package com.aicontent.main.presentation.daily

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel

@Composable
fun ItemDailyScreen(transaction: TransactionLocalModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = transaction.category)
        Text(text = transaction.account)
        Text(text = transaction.amount.toString())
    }
}