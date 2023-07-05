package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class AuthenticationSharedScreen : ScreenProvider{
    object LoginScreen : AuthenticationSharedScreen()
    object RegisterScreen : AuthenticationSharedScreen()
    object OTPScreen : AuthenticationSharedScreen()
}
