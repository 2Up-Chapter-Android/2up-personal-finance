package com.twoup.personalfinance.domain.model.authentication.otp

import com.twoup.personalfinance.const.ConstDefaultValue
import kotlinx.serialization.Serializable

@Serializable
data class ActiveUserResponseModel(
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)