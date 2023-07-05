package com.twoup.personalfinance.domain.usecase.authentication

import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository

class SendOtpUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(sendOtpRequest: SendOtpRequestModel) = repository.sendOtp(sendOtpRequest)
}