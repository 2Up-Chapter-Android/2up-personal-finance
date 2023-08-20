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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.MainScreenViewModel
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun DailyScreen(viewModel: DailyScreenViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val listTransaction = viewModel.transaction.value

    LaunchedEffect(navigator) {
        viewModel.loadNotes()
    }

    Column(modifier = Modifier.padding(8.dp)) {

        AnimatedVisibility(
            visible = listTransaction.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(20.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listTransaction) { transaction ->
                    ItemDailyScreen(
                        transaction
                    )
                }
            }
        }
    }
//    Text(MR.strings.daily_screen.desc().localized())


}