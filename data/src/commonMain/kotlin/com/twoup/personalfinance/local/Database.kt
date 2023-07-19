package com.twoup.personalfinance.local

import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup


class Database(databaseWrapper: PersonalFinanceDatabaseWrapper) {
    private val database = databaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {

        }
    }

    internal fun getAllLWallet(): List<Wallet> {
        return dbQuery.selectAllWallet().executeAsList().map { wallet ->
            mapSelecting(
                wallet.id,
                wallet.amount?.toInt(),
                wallet.description,
                wallet.name,
                wallet.walletGroup
            )
        }
    }

    fun insertWallet(
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

    private fun mapSelecting(
        id: String?,
        amount: Int?,
        description: String?,
        name: String?,
        walletGroup: String?,
    ): Wallet {
        return Wallet(
            amount!!, description!!, id!!, name!!, WalletGroup.valueOf(walletGroup!!)
        )
    }


}