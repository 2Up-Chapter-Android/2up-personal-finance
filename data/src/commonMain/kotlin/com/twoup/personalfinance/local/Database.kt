package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.transaction.Transaction
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity
import com.twoup.personalfinance.domain.model.transaction.TransactionType
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup
import comtwouppersonalfinancedatabase.GetAllInfoTransaction

class Database(databaseWrapper: PersonalFinanceDatabaseWrapper) : IDatabase {
    private val database = databaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries

//    CATEGORY
    override fun insertCategory(
        id: String,
        name: String,
        categoryId: String,
        userId: String
    ) {
        return dbQuery.insertCategory(
            id = id,
            name = name,
            categoryId = categoryId,
            userId = userId
        )
    }

    override fun deleteCategory(id : String){
        dbQuery.deletCategoryById(id)
    }

    override fun updateCategory(
        id : String,
        name: String,
        categoryId: String,
        userId: String,
        id_ : String
    ){
        return dbQuery.updateCategory(
            id = id,
            name = name,
            categoryId = categoryId,
            userId = userId,
            id_ = id_
        )
    }

    override fun getCategoryById(id : String): Category{
        return dbQuery.getCategoryById(id).executeAsOneOrNull()!!.mapToDomain()
    }
    override fun getAllCategory():List<Category>{
        return dbQuery.getAllCategory().executeAsList().map { it.mapToDomain() }
    }

    override fun clearAllWallets() {
        dbQuery.transaction {
            dbQuery.removeAllWallet()
        }
    }

    override fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllWallet()
        }
    }
    override fun getAllLWallet(): List<Wallet> {
        return dbQuery.selectAllWallet().executeAsList().map { it.mapToDomain() }
    }
    override fun insertWallet(
        id: String,
        amount: Int,
        description: String,
        name: String,
        walletGroup: WalletGroup
    ) {
        return dbQuery.insertWallet(
            id,
            amount.toLong(),
            description,
            name,
            walletGroup.name
        )
    }
    private fun comtwouppersonalfinancedatabase.Wallet.mapToDomain(): Wallet {
        return Wallet(
            amount = amount!!.toInt(),
            description = description!!,
            id = id!!,
            name = name!!,
            walletGroup = WalletGroup.valueOf(walletGroup!!)
        )
    }
    private fun comtwouppersonalfinancedatabase.PersonalFinanceDatabaseCategory.mapToDomain(): Category {
        return Category(
            id = id,
            name = name,
            categoryId = categoryId,
            userID = userId
        )
    }

    //    TRANSACTION
    override fun getAllTransaction(): List<TransactionEntity> {
        return listOf()
    }

    override fun insertTransaction(
        amount: Int,
        categoryId: String,
        createdAt: Long,
        description: String,
        id: String,
        note: String,
        type: com.twoup.personalfinance.domain.model.transaction.TransactionType,
        updatedAt: Long,
        walletId: String
    ) {

    }

    override fun clearAllTransactions() {

    }

    override fun getAllListTransactionInfo(): List<GetAllInfoTransaction> {
        return dbQuery.getAllInfoTransaction().executeAsList()
    }

    private fun GetAllInfoTransaction.mapToDomain() = Transaction(
        income = transaction_income.toInt(),
        expenses = transaction_expenses.toInt(),
        category = Category(
            id = transaction_category,
            name = category_name ?: "",
            categoryId = "",
            userID = ""
        ),
        createdAt = transaction_created,
        description = transaction_description,
        id = transaction_id.toString(),
        note = "",
        type = TransactionType.DEFAULT,
        updatedAt = 0,
        wallet = Wallet(
            amount = wallet_amount?.toInt() ?: 0,
            description = wallet_description ?: "",
            id = wallet_id ?: "",
            name = wallet_name ?: "",
            walletGroup = WalletGroup.valueOf(wallet_group ?: "")
        )
    )
}