package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.authentication.login.LoginResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterResponseModel
import com.twoup.personalfinance.remote.dto.authentication.login.LoginResponse
import com.twoup.personalfinance.remote.dto.authentication.otp.ActiveUserResponse
import com.twoup.personalfinance.remote.dto.authentication.otp.SendOtpResponse
import com.twoup.personalfinance.remote.dto.authentication.register.RegisterResponse

fun RegisterResponse?.mapToDomain(): RegisterResponseModel = RegisterResponseModel(
    status = this?.status ?: 0,
    statusMessage = this?.status_message ?: "",
    timestamp = this?.timestamp ?: "",
    data = this?.data?.mapToDomain() ?: RegisterResponseModel.RegisterResponseData()
)

fun RegisterResponse.Data?.mapToDomain(): RegisterResponseModel.RegisterResponseData =
    RegisterResponseModel.RegisterResponseData(
        id = this?.id ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        email = this?.email ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        username = this?.username ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        fullName = this?.full_name ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        activated = this?.activated ?: ConstDefaultValue.DEFAULT_VALUE_STRING
    )

fun LoginResponse?.mapToDomain(): LoginResponseModel = LoginResponseModel(
    status = this?.status ?: 0,
    message = this?.message ?: "",
    data = this?.data.mapToDomain(),
)


private fun LoginResponse.LoginResponseData?.mapToDomain(): LoginResponseModel.LoginResponseData =
    LoginResponseModel.LoginResponseData(
        accessToken = this?.access_token ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        accessTokenLifeTime = this?.access_token_life_time ?: 0,
        refreshToken = this?.refresh_token ?: ConstDefaultValue.DEFAULT_VALUE_STRING,
        refreshTokenLifeTime = this?.refresh_token_life_time ?: 0,
        tokenType = this?.token_type ?: ""
    )

fun SendOtpResponse?.mapToDomain(): SendOtpResponseModel =
    SendOtpResponseModel(
        status = this?.status ?: 0,
        statusMessage = this?.status_message ?: "",
        timestamp = this?.timestamp ?: ""
    )


fun ActiveUserResponse?.mapToDomain(): ActiveUserResponseModel =
    ActiveUserResponseModel(
        status = this?.status ?: 0,
        statusMessage = this?.status_message ?: "",
        timestamp = this?.timestamp ?: "",
    )
