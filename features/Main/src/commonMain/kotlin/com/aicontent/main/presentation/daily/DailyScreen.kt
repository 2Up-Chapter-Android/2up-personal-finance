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
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.BudgetBox
import com.aicontent.main.presentation.TopAppBar
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import dev.icerock.moko.resources.compose.painterResource

class DailyScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val transactionScreen = rememberScreen(TransactionSharedScreen.CreateTransactionScreen)
        val viewModel = rememberScreenModel { DailyScreenViewModel() }

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

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        // Add a single item
                        item {
                            Text(text = "First item")
                        }

                        // Add 5 items
                        items(5) { index ->
                            ItemTransaction()
                            Divider(color = Color.Black, thickness = 0.5.dp)
                        }

                        // Add another single item
                        item {
                            Text(text = "Last item")
                        }
                    }
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//
//                        Text(MR.strings.daily_screen.desc().localized())
//                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(transactionScreen)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }

    @Composable
    fun ItemTransaction() {
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
                Text(modifier = Modifier.padding(vertical = 4.dp) ,text = "First item", color = Color.Black, fontSize = 14.sp)
                Text(text = "First item", fontSize = 12.sp)
            }

            Text(modifier = Modifier.padding(end = 8.dp),text = "First item")
        }
    }
}