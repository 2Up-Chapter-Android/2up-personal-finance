package com.twoup.personalfinance.domain.model.authentication.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    val username: String = "",
    val full_name: String = "",
    val email: String = "",
    val password: String = "",
    val confirm_password: String = "",
)