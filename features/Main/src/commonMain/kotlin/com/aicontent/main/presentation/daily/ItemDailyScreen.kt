package com.aicontent.main.presentation.daily

import PersonalFinance.features.Main.MR
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.aicontent.main.theme.*
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun ItemDailyScreen(transaction: TransactionLocalModel) {

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
            text = transaction.category,
            fontSize = font_size_text_item_budge,
            color = Color.Gray
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(vertical = padding_vertical_text_daily_item),
                text = transaction.description,
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(text = transaction.account, fontSize = font_size_text_item_budge)
        }

        Text(
            modifier = Modifier.padding(end = padding_end_text_daily_item),
            text = transaction.amount.toString()
        )
    }
}