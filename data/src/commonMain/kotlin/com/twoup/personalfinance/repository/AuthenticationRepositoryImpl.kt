package com.twoup.personalfinance.repository

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.model.authentication.login.LoginResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.model.authentication.register.RegisterResponseModel
import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository
import com.twoup.personalfinance.local.SecureStorageKey
import com.twoup.personalfinance.local.SecureStorageWrapper
import com.twoup.personalfinance.mapping.mapToDomain
import com.twoup.personalfinance.remote.services.authentication.AuthenticationDataSource
import com.twoup.personalfinance.remote.util.safeApiCall

class AuthenticationRepositoryImpl(
    private val dataSource: AuthenticationDataSource,
    private val secureStorageWrapperImpl: SecureStorageWrapper
) : AuthenticationRepository {

    override suspend fun login(loginRequest: LoginRequestModel): Result<LoginResponseModel> {
        val loginResponse = safeApiCall {
            dataSource.login(loginRequest)
        }.map { it.mapToDomain() }.onSuccess {
            secureStorageWrapperImpl.saveValue(
                SecureStorageKey.ACCESS_TOKEN, it.data.accessToken
            )
            secureStorageWrapperImpl.saveValue(
                SecureStorageKey.REFRESH_TOKEN, it.data.refreshToken
            )
        }
        return loginResponse
    }

    override suspend fun register(registerRequest: RegisterRequestModel): Result<RegisterResponseModel> =
        safeApiCall { dataSource.register(registerRequest) }.map { it.mapToDomain() }

    override suspend fun sendOtp(sendOTPRequest: SendOtpRequestModel): Result<SendOtpResponseModel> =
        safeApiCall { dataSource.sendOtp(sendOTPRequest) }.map {
            it.mapToDomain()
        }

    override suspend fun activeUser(activeUserRequest: ActiveUserRequestModel): Result<ActiveUserResponseModel> =
        safeApiCall { dataSource.activeUser(activeUserRequest) }.map {
            it.mapToDomain()
        }
}