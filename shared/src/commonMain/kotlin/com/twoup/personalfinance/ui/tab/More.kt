package com.twoup.personalfinance.ui.tab

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions

internal object More: DestinationTab {
    override val route: String
        get() = "wallet_tab"

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.More)

            return remember {
                TabOptions(
                    index = 3u,
                    title = "More",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
//        TabContent()
//        Navigator(MoreScreen())
        Text("More Screen", fontSize = 20.sp)

    }
}