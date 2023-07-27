//package com.aicontent.category.di
//
//import cafe.adriel.voyager.core.registry.ScreenRegistry
//import com.aicontent.category.presentation.ui.CategoryHttpStatus
//import com.aicontent.category.presentation.ui.CategoryScreen
//import com.twoup.personalfinance.navigation.SharedScreenCategory
//import org.koin.dsl.module
//
//
//fun categoryNavigationModule() = module{
//    ScreenRegistry.register<SharedScreenCategory.CategoryScreen>{
//        CategoryScreen()
//    }
//    ScreenRegistry.register<SharedScreenCategory.CategoryHttpStatus>{
//        CategoryHttpStatus()
//    }
//}