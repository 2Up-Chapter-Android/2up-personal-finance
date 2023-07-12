package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreenCategory : ScreenProvider {
    object CategoryScreen : SharedScreenCategory()
    object CategoryHttpStatus : SharedScreenCategory()
}
