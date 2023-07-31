

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.aicontent.category.presentation.ui.CategoryHttpStatus
import com.aicontent.category.presentation.ui.CategoryScreen
import com.twoup.personalfinance.navigation.CategorySharedScreen
import org.koin.dsl.module


fun categoryNavigationModule() = module{
    ScreenRegistry.register<CategorySharedScreen.CategoryScreen>{
        CategoryScreen()
    }
    ScreenRegistry.register<CategorySharedScreen.CategoryHttpStatus>{
        CategoryHttpStatus()
    }
}