package com.twoup.personalfinance.domain.usecase.localTransaction.category//package com.twoup.personalfinance.domain.usecase.localTransaction
//
//import com.twoup.personalfinance.domain.model.transaction.category.subCategories.SubCategoriesLocalModel
//import com.twoup.personalfinance.local.transaction.TransactionLocalDataSource
//import kotlinx.coroutines.DelicateCoroutinesApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class UseCaseGetAllSubCategories(private val dataSource: TransactionLocalDataSource) {
//
//    val subCategoriesState: MutableStateFlow<List<SubCategoriesLocalModel>> =
//        MutableStateFlow(listOf())
//
//    @OptIn(DelicateCoroutinesApi::class)
//    fun getAllSubCategories() {
//        GlobalScope.launch {
//            val subCategories = withContext(Dispatchers.Default) {
//                dataSource.getAllSubCategories()
//            }
//            subCategoriesState.value = subCategories
//        }
//    }
//}