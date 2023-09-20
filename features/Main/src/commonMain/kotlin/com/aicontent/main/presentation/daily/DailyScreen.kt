package com.aicontent.main.presentation.daily

import PersonalFinance.features.Main.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.MainScreenViewModel
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun DailyScreen(viewModel: DailyScreenViewModel) {

    val navigator = LocalNavigator.currentOrThrow
    val listTransaction = viewModel.transaction.value

    LaunchedEffect(navigator) {
        viewModel.loadNotes()
    }

    Column(modifier = Modifier) {
        AnimatedVisibility(
            visible = listTransaction.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(listTransaction) { transaction ->
                    val itemTransactionScreen = rememberScreen(MainScreenSharedScreen.ItemTransaction(transaction))

                    if (transaction.transferBalance > 0) {
                        ItemDailyTransferScreen(transaction) {
                            navigator.push(itemTransactionScreen)
                        }
                    } else if (transaction.income > 0 || transaction.expenses > 0) {
                        ItemDailyScreen(transaction) {
                            navigator.push(itemTransactionScreen)
                        }
                    }
                }
            }
        }
    }
}