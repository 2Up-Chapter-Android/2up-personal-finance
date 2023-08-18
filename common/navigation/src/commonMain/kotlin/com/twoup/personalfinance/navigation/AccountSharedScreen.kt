package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class AccountSharedScreen : ScreenProvider {
    object AccountScreen : MainScreenSharedScreen()

}
