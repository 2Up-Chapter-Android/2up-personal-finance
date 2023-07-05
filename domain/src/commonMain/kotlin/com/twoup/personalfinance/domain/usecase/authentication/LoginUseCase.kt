package com.twoup.personalfinance.domain.usecase.authentication

import com.twoup.personalfinance.domain.model.authentication.login.LoginRequestModel
import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository

class LoginUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(loginRequest: LoginRequestModel) = repository.login(loginRequest)
}