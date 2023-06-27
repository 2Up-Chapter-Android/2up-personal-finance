package com.twoup.personalfinance.authentication.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.twoup.personalfinance.authentication.presentation.ui.login.LoginScreen
import com.twoup.personalfinance.authentication.presentation.ui.otp.OTPScreen
import com.twoup.personalfinance.authentication.presentation.ui.register.RegisterScreen
import com.twoup.personalfinance.navigation.AuthenticationSharedScreen
import org.koin.dsl.module

fun authenticationNavigationModule() = module {
    ScreenRegistry.register<AuthenticationSharedScreen.LoginScreen> {
        LoginScreen()
    }
    ScreenRegistry.register<AuthenticationSharedScreen.RegisterScreen> {
        RegisterScreen()
    }
    ScreenRegistry.register<AuthenticationSharedScreen.OTPScreen> {
        OTPScreen()
    }
}