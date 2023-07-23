package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup


class Database(databaseWrapper: PersonalFinanceDatabaseWrapper): IDatabase {
    private val database = databaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries

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
            amount =  amount!!.toInt(),
            description = description!!,
            id = id!!,
            name = name!!,
            walletGroup = WalletGroup.valueOf(walletGroup!!)
        )
    }
}