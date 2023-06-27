package com.twoup.personalfinance.domain.repository.authentication

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.model.authentication.login.LoginResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterResponseModel

interface AuthenticationRepository {
    suspend fun login(loginRequest: LoginRequestModel): Result<LoginResponseModel>

    suspend fun register(registerRequest: RegisterRequestModel): Result<RegisterResponseModel>

    suspend fun sendOtp(sendOTPRequest: SendOtpRequestModel): Result<SendOtpResponseModel>

    suspend fun activeUser(activeUserRequest: ActiveUserRequestModel): Result<ActiveUserResponseModel>
}