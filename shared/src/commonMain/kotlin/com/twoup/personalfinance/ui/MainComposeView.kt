package com.twoup.personalfinance.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.aicontent.category.presentation.ui.CategoryHttpStatus
import com.aicontent.category.presentation.ui.CategoryScreen
import com.aicontent.main.presentation.MainScreen
import com.twoup.personalfinance.authentication.presentation.ui.login.LoginScreen
import com.twoup.personalfinance.authentication.presentation.ui.otp.OTPScreen
import com.twoup.personalfinance.resources.LightColorPalette
import com.twoup.personalfinance.transaction.presentation.dashboard.TransactionDashboardScreen

@Composable
internal fun MainComposeView(modifier: Modifier = Modifier) {
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
    Scaffold {
        Navigator(LoginScreen())
//        Navigator(TransactionDashboardScreen())
//        Navigator(CategoryScreen())
//        Navigator(MainScreen())
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