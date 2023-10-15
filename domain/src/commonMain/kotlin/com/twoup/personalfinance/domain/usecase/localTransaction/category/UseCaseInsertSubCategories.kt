package com.twoup.personalfinance.domain.usecase.localTransaction.category//package com.twoup.personalfinance.domain.usecase.localTransaction
//
//import com.twoup.personalfinance.domain.model.transaction.category.subCategories.SubCategoriesLocalModel
//import com.twoup.personalfinance.local.transaction.TransactionLocalDataSource
//import kotlinx.coroutines.DelicateCoroutinesApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class UseCaseInsertSubCategories(private val dataSource: TransactionLocalDataSource) {
//
//    @OptIn(DelicateCoroutinesApi::class)
//    fun insertSubCategories(subCategories: SubCategoriesLocalModel) {
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                dataSource.insertSubCategory(subCategories)
//            }
//        }
//    }
//}