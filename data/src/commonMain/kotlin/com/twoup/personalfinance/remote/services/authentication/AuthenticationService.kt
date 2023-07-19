package com.twoup.personalfinance.remote.services.authentication

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.remote.dto.authentication.login.LoginResponse
import com.twoup.personalfinance.remote.dto.authentication.otp.ActiveUserResponse
import com.twoup.personalfinance.remote.dto.authentication.otp.SendOtpResponse
import com.twoup.personalfinance.remote.dto.authentication.register.RegisterResponse
import com.twoup.personalfinance.utils.data.Resource
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthenticationService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequestModel): Resource<LoginResponse>

    @POST("auth/users/register")
    suspend fun register(@Body registerRequest: RegisterRequestModel): Resource<RegisterResponse>

    @POST("auth/otp/resend")
    suspend fun sendOtp(@Body sendOTPRequest: SendOtpRequestModel): Resource<SendOtpResponse>

    @POST("auth/users/active")
    suspend fun activeUser(@Body activeUserRequest: ActiveUserRequestModel): Resource<ActiveUserResponse>
}