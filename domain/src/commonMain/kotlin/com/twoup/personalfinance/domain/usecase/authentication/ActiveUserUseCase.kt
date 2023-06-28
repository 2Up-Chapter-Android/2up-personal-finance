package com.twoup.personalfinance.domain.usecase.authentication

import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository

class ActiveUserUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(activeUserRequest: ActiveUserRequestModel) = repository.activeUser(activeUserRequest)
}