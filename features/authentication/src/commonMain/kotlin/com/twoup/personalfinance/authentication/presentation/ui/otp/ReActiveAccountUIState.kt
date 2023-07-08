package com.twoup.personalfinance.authentication.presentation.ui.otp

data class ReActiveAccountUIState(
    val isLoading: Boolean = false,
    val emailError: String = "",
    val isNullEmail: Boolean = true,
){
    val enableSendButton get() = !isLoading && !isNullEmail
    val visibilityEmailError get() = emailError.isNotEmpty()
}
