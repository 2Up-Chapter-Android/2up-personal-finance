package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import com.twoup.personalfinance.domain.model.transaction.TransactionType
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup
import comtwouppersonalfinancedatabase.GetAllInfoTransaction

interface IDatabase {
//    WALLET
    fun clearAllWallets()
    fun clearDatabase()
    fun getAllLWallet(): List<Wallet>
    fun insertWallet(
        id: String,
        amount: Int,
        description: String,
        name: String,
        walletGroup: WalletGroup
    )

//    CATEGORY
    fun insertCategory(
        id: String,
        name: String,
        categoryId: String,
        userId : String
    )
    fun updateCategory(
        id: String,
        name: String,
        categoryId: String,
        userId : String,
        id_ : String
    )
    fun deleteCategory(id: String)
    fun getCategoryById(id : String): Category
    fun getAllCategory(): List<Category>

//    TRANSACTION
    fun getAllTransaction(): List<TransactionEntity>

    fun insertTransaction(
        amount: Int,
        categoryId: String,
        createdAt: Long,
        description: String,
        id: String,
        note: String,
        type: TransactionType,
        updatedAt: Long,
        walletId: String
    )

    fun clearAllTransactions()
    fun getAllListTransactionInfo(): List<GetAllInfoTransaction>
}