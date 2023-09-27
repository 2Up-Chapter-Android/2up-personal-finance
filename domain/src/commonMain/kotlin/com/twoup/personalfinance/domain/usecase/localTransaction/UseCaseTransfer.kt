//package com.twoup.personalfinance.domain.usecase.localTransaction
//
//import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
//import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
//import kotlinx.coroutines.DelicateCoroutinesApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//
//class UseCaseTransfer(private val dataSource: TransactionLocalDataSource) {
//
//    @OptIn(DelicateCoroutinesApi::class)
//    fun updateTransaction(transaction: TransactionLocalModel, loadNote: Unit) {
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                dataSource.updateTransaction(
//                    TransactionLocalModel(
//                        transaction_id = transaction.transaction_id,
//                        income = transaction.income,
//                        expenses = transaction.expenses,
//                        description = transaction.description,
//                        created = transaction.created,
//                        category = transaction.category,
//                        account = transaction.account,
//                        selectIndex = transaction.selectIndex
//                    )
//                )
//            }
//            loadNote
//        }
//    }
//}
