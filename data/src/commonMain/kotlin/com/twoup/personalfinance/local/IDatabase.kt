package com.twoup.personalfinance.local

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
//    fun insertTransaction(
//        id: String,
//        amount: Int,
//        description: String,
//        name: String,
//        walletGroup: WalletGroup
//    )
}