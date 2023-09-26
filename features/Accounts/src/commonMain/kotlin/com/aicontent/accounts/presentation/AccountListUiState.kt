package com.aicontent.accounts.presentation

data class AccountListUiState(
    var account_id: Long = 0,
    var account_name: String = "",
    var account_type: String = "",
    var description: String = "",
    var income: Long = 0,
    var expense: Long = 0,
    var transfer : Long = 0,
    var account_to : String = "",
    var account_from : String = ""
)