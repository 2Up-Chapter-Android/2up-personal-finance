package com.aicontent.accounts.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AccountListViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllAccount: UseCaseGetAllAccount by inject()
    private val useCaseUpdateAccountById: UseCaseUpdateAccountById by inject()
    private val useCaseInsertAccount: UseCaseInsertAccount by inject()
    private val useCaseDeleteAccountById: UseCaseDeleteAccountById by inject()
    private val useCaseGetAllTransaction : UseCaseGetAllTransaction by inject()

    val accounts: StateFlow<List<AccountLocalModel>> get() = useCaseGetAllAccount.accountState.asStateFlow()
    val transactions : StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    private val _accountUiState = MutableStateFlow(AccountListUiState())
    val accountUiState = _accountUiState.asStateFlow()


    init {
        loadAccount()
    }

    fun loadAccount() {
        useCaseGetAllAccount.getAllAccount()
        useCaseGetAllTransaction.getAllTransaction()
    }

    fun updateAccount(account: AccountLocalModel) {
        useCaseUpdateAccountById.updateAccount(account, loadAccount())
    }

    fun deleteAccount(id: Long) {
        useCaseDeleteAccountById.deleteAccountById(id, loadAccount())
    }

    fun insertAccount(account: AccountLocalModel){
        useCaseInsertAccount.insertAccount(account)
    }
}