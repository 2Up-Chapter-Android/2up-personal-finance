package com.twoup.personalfinance.domain.repository.authentication

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.model.authentication.login.LoginResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterResponseModel
import com.twoup.personalfinance.utils.data.Resource

interface AuthenticationRepository {
    suspend fun login(loginRequest: LoginRequestModel): Resource<LoginResponseModel>

    suspend fun register(registerRequest: RegisterRequestModel): Resource<RegisterResponseModel>

    suspend fun sendOtp(sendOTPRequest: SendOtpRequestModel): Resource<SendOtpResponseModel>

    suspend fun activeUser(activeUserRequest: ActiveUserRequestModel): Resource<ActiveUserResponseModel>
}