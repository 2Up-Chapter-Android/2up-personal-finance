package com.aicontent.accounts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aicontent.accounts.theme.font_size_text_item_category
import com.aicontent.accounts.theme.padding_end_text_daily_item
import com.aicontent.accounts.theme.padding_vertical_text_daily_item
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel

@Composable
fun ItemDailyScreen(
    transaction: TransactionLocalModel,
    onNoteClick: () -> Unit,
    isTransfer: Boolean = false
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp).clickable(onClick = onNoteClick)
        ) {
            Text(
                modifier = Modifier.padding(start = padding_end_text_daily_item).width(80.dp),
                text = if (isTransfer) "Transfer" else transaction.transaction_category,
                fontSize = font_size_text_item_category,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(vertical = padding_vertical_text_daily_item),
                    text = transaction.transaction_description,
                    color = Color.Black,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (isTransfer) "${transaction.transaction_accountFrom} -> ${transaction.transaction_accountTo}" else transaction.transaction_account,
                    fontSize = font_size_text_item_category,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.padding(1.dp))

            val colorText = when {
                transaction.transaction_income - transaction.transaction_expenses > 0 -> Color.Blue
                transaction.transaction_expenses - transaction.transaction_income > 0 -> Color.Red
                else -> Color.Black
            }
            val incomeOrExpenses = when {
                transaction.transaction_income - transaction.transaction_expenses > 0 -> transaction.transaction_income
                transaction.transaction_expenses - transaction.transaction_income > 0 -> transaction.transaction_expenses
                else -> 0
            }
            Text(
                modifier = Modifier.padding(end = padding_end_text_daily_item),
                text = if(isTransfer) "${transaction.transaction_transfer} đ" else "$incomeOrExpenses đ",
                color = colorText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = adjustFontSize(incomeOrExpenses.toString()).sp
            )
        }
    }
}
