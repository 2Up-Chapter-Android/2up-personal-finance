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
import com.aicontent.main.theme.*
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun DailyScreen(listTransaction: List<TransactionEntity>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = padding_vertical_lazy_column),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Add a single item
        item {
            Text(text = "First item")
        }

        // Add 5 items
        items(listTransaction.size) { index ->
            ItemTransaction(listTransaction[index])
            Divider(color = Color.Gray, thickness = 0.5.dp)
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
                .padding(
                    top = padding_image_daily_item,
                    start = padding_image_daily_item,
                    bottom = padding_image_daily_item
                )
                .width(width_image_daily_item)
                .height(height_image_daily_item),
            painter = painterResource(MR.images.logo_otp),

            )

        Text(
            modifier = Modifier.padding(end = padding_end_text_daily_item),
            text = transactionEntity.categoryId,
            fontSize = font_size_text_item_budge,
            color = Color.Gray
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(vertical = padding_vertical_text_daily_item),
                text = transactionEntity.note,
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(text = transactionEntity.walletId, fontSize = font_size_text_item_budge)
        }

        Text(
            modifier = Modifier.padding(end = padding_end_text_daily_item),
            text = transactionEntity.amount.toString()
        )
    }
}