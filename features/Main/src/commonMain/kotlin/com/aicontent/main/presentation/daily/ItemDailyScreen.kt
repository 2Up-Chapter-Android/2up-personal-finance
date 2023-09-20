package com.aicontent.main.presentation.daily

import PersonalFinance.features.Main.MR
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aicontent.main.theme.*
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun ItemDailyScreen(transaction: TransactionLocalModel, onNoteClick: () -> Unit) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp).clickable(onClick = onNoteClick)
        ) {
//        Image(
//            contentDescription = "My Image",
//            modifier = Modifier
//                .padding(
//                    top = padding_image_daily_item,
//                    start = padding_image_daily_item,
//                    bottom = padding_image_daily_item
//                )
//                .width(width_image_daily_item)
//                .height(height_image_daily_item),
//            painter = painterResource(MR.images.logo_otp),
//            )
            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item).width(80.dp),
                text = transaction.category,
                fontSize = font_size_text_item_category,
                color = Color.Gray
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(vertical = padding_vertical_text_daily_item),
                    text = transaction.description,
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(text = transaction.account, fontSize = font_size_text_item_category)
            }

            val colorText = when {
                transaction.income - transaction.expenses > 0 -> Color.Blue
                transaction.expenses - transaction.income > 0 -> Color.Red
                else -> Color.Black
            }

            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item),
                text = "${transaction.income - transaction.expenses}đ",
                color = colorText
            )
        }
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
    }
}

@Composable
fun ItemDailyTransferScreen(transaction: TransactionLocalModel, onNoteClick: () -> Unit) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp).clickable(onClick = onNoteClick)
        ) {
//        Image(
//            contentDescription = "My Image",
//            modifier = Modifier
//                .padding(
//                    top = padding_image_daily_item,
//                    start = padding_image_daily_item,
//                    bottom = padding_image_daily_item
//                )
//                .width(width_image_daily_item)
//                .height(height_image_daily_item),
//            painter = painterResource(MR.images.logo_otp),
//            )
            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item).width(80.dp),
                text = "Transfer",
                fontSize = font_size_text_item_category,
                color = Color.Gray
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(vertical = padding_vertical_text_daily_item),
                    text = transaction.description,
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "${transaction.accountFrom} -> ${transaction.accountTo}",
                    fontSize = font_size_text_item_category
                )
            }

            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item),
                text = "${transaction.transferBalance}đ",
                color = Color.Black
            )
        }
        Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
    }
}