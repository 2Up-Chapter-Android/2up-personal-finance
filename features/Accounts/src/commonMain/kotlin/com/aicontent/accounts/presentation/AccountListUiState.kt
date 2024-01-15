package com.aicontent.accounts.presentation

data class AccountListUiState(
    var accountId: Long = 0,
    var accountName: String = "",
    var accountType: String = "",
    var description: String = "",
    var income: Long = 0,
    var expense: Long = 0,
    var transfer : Long = 0,
    var accountTo : String = "",
    var accountFrom : String = ""
)