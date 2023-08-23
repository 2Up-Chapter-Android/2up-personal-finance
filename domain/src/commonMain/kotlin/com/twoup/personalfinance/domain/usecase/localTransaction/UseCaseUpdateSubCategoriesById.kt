//package com.twoup.personalfinance.domain.usecase.localTransaction
//
//import com.twoup.personalfinance.domain.model.transaction.category.subCategories.SubCategoriesLocalModel
//import com.twoup.personalfinance.local.transaction.TransactionLocalDataSource
//import kotlinx.coroutines.DelicateCoroutinesApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class UseCaseUpdateSubCategoriesById(private val dataSource: TransactionLocalDataSource) {
//
//    @OptIn(DelicateCoroutinesApi::class)
//    fun updateSubCategories(subCategories: SubCategoriesLocalModel, loadNote: Unit) {
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                dataSource.updateSubCategories(
//                    SubCategoriesLocalModel(
//                        subCategories_id = subCategories.subCategories_id,
//                        subCategories_name = subCategories.subCategories_name,
//                        category_id = subCategories.category_id
//                    )
//                )
//            }
//            loadNote
//        }
//    }
//}