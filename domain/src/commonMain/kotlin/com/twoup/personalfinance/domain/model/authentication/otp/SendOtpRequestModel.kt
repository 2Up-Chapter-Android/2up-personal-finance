package com.twoup.personalfinance.domain.model.authentication.otp

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpRequestModel(
    val email: String
)
