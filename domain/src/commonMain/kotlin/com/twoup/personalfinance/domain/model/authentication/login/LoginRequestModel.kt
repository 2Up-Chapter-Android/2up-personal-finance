package com.twoup.personalfinance.domain.model.authentication.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    val password: String,
    val email: String
)