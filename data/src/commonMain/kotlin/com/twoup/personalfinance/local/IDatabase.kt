package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup

interface IDatabase {
    fun clearDatabase()
    fun getAllLWallet(): List<Wallet>
    fun insertWallet(
        id: String,
        amount: Int,
        description: String,
        name: String,
        walletGroup: WalletGroup
    )
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

}