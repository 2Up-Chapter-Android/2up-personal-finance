package com.twoup.personalfinance.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.twoup.personalfinance.resources.LightColorPalette
import com.twoup.personalfinance.ui.tab.BrowserTab
import com.twoup.personalfinance.ui.tab.DAppTab
import com.twoup.personalfinance.ui.tab.NftTab
import com.twoup.personalfinance.ui.tab.SwapTab
import com.twoup.personalfinance.ui.tab.WalletTab
import com.twoup.personalfinance.viewmodel.ApplicationViewModel

@Composable
internal fun MainComposeView(viewModel: ApplicationViewModel, modifier: Modifier = Modifier) {
    PersonalFinanceTheme {
//        Text(stringResource(MR.strings.))
        Content()
//        SampleApplication()
//        BottomNavigationView(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
internal fun PersonalFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
    val colors =  LightColorPalette
//    }

    MaterialTheme(
        colors = colors,
        content = content,
    )
}

@Composable
internal fun Content() {
    TabNavigator(WalletTab) { tabNavigator ->
        Scaffold(
            content = {
                CurrentTab()
            },
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
                    TabNavigationItem(WalletTab)
                    TabNavigationItem(DAppTab)
                    TabNavigationItem(SwapTab)
                    TabNavigationItem(NftTab)
                    TabNavigationItem(BrowserTab)
                }
            }
        )
    }
}

@Composable
internal fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(text = tab.options.title) },
        selectedContentColor = MaterialTheme.colors.secondaryVariant,
        unselectedContentColor = MaterialTheme.colors.onPrimary,
    )
}