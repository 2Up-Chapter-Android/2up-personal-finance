package com.twoup.personalfinance

//import androidx.compose.ui.window.Application
import androidx.compose.ui.window.ComposeUIViewController
import com.twoup.personalfinance.ui.MainComposeView
import platform.UIKit.UIViewController

//import com.twoup.personalfinance.viewmodel.ApplicationViewModel

fun getRootController() : UIViewController = ComposeUIViewController {
    MainComposeView()
}