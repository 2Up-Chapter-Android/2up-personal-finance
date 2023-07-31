package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup

class Database(databaseWrapper: PersonalFinanceDatabaseWrapper) : IDatabase {
    private val database = databaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries

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
}