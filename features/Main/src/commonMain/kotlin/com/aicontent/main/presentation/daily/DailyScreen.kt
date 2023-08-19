package com.aicontent.main.presentation.daily


import PersonalFinance.features.Main.MR
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun DailyScreen(listTransaction: MutableList<TransactionEntity>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Add a single item
        item {
            Text(text = "First item")
        }

        // Add 5 items
        items(listTransaction.size) { index ->
            ItemTransaction(listTransaction[index])
            Divider(color = Color.Black, thickness = 0.5.dp)
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

@Composable
fun ItemTransaction(transactionEntity: TransactionEntity) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            contentDescription = "My Image",
            modifier = Modifier
                .padding(16.dp)
                .width(32.dp)
                .height(32.dp),
            painter = painterResource(MR.images.logo_otp),

            )

        Column(modifier = Modifier.weight(1f)) {
            Text(modifier = Modifier.padding(vertical = 4.dp) ,text = transactionEntity.amount.toString(), color = Color.Black, fontSize = 14.sp)
            Text(text = "First item", fontSize = 12.sp)
        }

        Text(modifier = Modifier.padding(end = 8.dp),text = "First item")
    }
}