package com.twoup.personalfinance.transaction.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.navigation.TransactionSharedScreen

class TransactionDashboardScreen: Screen {
    @Composable
    override fun Content() {
        TransactionDashboardScreen()
    }

    @Composable
    fun TransactionDashboardScreen() {
        val navigator = LocalNavigator.currentOrThrow
        val createTransScreen = rememberScreen(TransactionSharedScreen.CreateTransactionScreen)
        Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Column {
                Text(text = "Hello Transaction Dashboard Screen", color = Color.Black, fontSize = 50.sp)
            }
            Button(
                onClick = {
                    navigator.push(createTransScreen)
                },
                shape = CircleShape,
                modifier = Modifier.size(50.dp).align(Alignment.BottomEnd),
                colors = ButtonDefaults.buttonColors(Color.Red),
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}