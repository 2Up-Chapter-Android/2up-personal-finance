package com.twoup.personalfinance.domain.usecase.authentication

import com.twoup.personalfinance.domain.model.authentication.register.RegisterRequestModel
import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository

class RegisterUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(registerRequest: RegisterRequestModel) = repository.register(registerRequest)
}