package com.twoup.personalfinance.domain.model.authentication.login

import com.twoup.personalfinance.const.ConstDefaultValue
import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseModel(
    val data: LoginResponseData = LoginResponseData(),
    val message: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val status: Int = 0
){
    @Serializable
    data class LoginResponseData(
        val accessToken: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val accessTokenLifeTime: Int = 0,
        val refreshToken: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val refreshTokenLifeTime: Long = 0,
        val tokenType: String = ""
    )
}
