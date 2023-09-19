package com.twoup.personalfinance.ui.tab

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.aicontent.main.presentation.MainScreen

//import com.twoup.personalfinance.features.note.ui.Note.setting.SettingsScreen

internal object Status: DestinationTab {
    override val route: String
        get() = "wallet_tab"

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.AddChart)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Status",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
//        PINScreen {
//            println("PIN $it")
//        }
//        Navigator(StatusScreen())
//        TabContent()
        Text("Status Screen", fontSize = 20.sp)

    }
}