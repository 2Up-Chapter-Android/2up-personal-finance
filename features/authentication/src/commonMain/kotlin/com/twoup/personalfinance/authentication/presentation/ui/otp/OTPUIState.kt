package com.twoup.personalfinance.authentication.presentation.ui.otp

data class OTPUIState(
    val isLoading: Boolean = false,
    val isFullFillOtp: Boolean = false
) {
    val enableActiveButton
        get() = !isLoading && isFullFillOtp
}
