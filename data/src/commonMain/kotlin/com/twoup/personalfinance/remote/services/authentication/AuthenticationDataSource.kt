package com.twoup.personalfinance.remote.services.authentication

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel


class AuthenticationDataSource(private val service: AuthenticationService) {
    suspend fun login(loginRequest: LoginRequestModel) = service.login(loginRequest)
    suspend fun register(registerRequest: RegisterRequestModel) = service.register(registerRequest)
    suspend fun sendOtp(sendOTPRequest: SendOtpRequestModel) = service.sendOtp(sendOTPRequest)
    suspend fun activeUser(activeUserRequest: ActiveUserRequestModel) = service.activeUser(activeUserRequest)
}