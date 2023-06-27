package com.twoup.personalfinance.domain.model.authentication.otp

import kotlinx.serialization.Serializable

@Serializable
data class ActiveUserRequestModel(
    val email: String,
    val otp: String
)
