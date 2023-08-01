package com.aicontent.main.presentation.total

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.aicontent.main.presentation.BudgetBox
import com.aicontent.main.presentation.TapBarViewModel
import com.aicontent.main.presentation.TopAppBar
import PersonalFinance.features.Main.MR
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

class TotalScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { TotalScreenViewModel() }
        Scaffold(
            topBar = {
                TopAppBar(
                    onSearchClicked = {},
                    onBookMark = {},
                    onAnalysis = {},
                    viewModel.selectedTabIndex.value
                )
            },
            content = {
                Column{
                    BudgetBox()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(MR.strings.total_screen.desc().localized())
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}
