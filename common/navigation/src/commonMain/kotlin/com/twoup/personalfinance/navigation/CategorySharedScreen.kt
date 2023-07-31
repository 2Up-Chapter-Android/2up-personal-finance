package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider


sealed class CategorySharedScreen : ScreenProvider {
    object CategoryHttpStatus : CategorySharedScreen()
    object CategoryScreen : CategorySharedScreen()
}