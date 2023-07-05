package com.twoup.personalfinance.remote.dto.authentication.otp

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val status: Int = 0,
    val status_message: String = "",
    val timestamp: String = ""
)