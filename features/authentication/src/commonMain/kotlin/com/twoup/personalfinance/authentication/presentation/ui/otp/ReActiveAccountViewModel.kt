package com.twoup.personalfinance.authentication.presentation.ui.otp

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.remote.util.Resource
import com.twoup.personalfinance.remote.util.toResource
import com.twoup.personalfinance.utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReActiveAccountViewModel: ScreenModel, KoinComponent {
    private val sendOtpUseCase: SendOtpUseCase by inject()

    private val _emailInput = MutableStateFlow("")
    val emailInput = _emailInput.asStateFlow()

    private val _reActiveAccountUIState = MutableStateFlow(ReActiveAccountUIState())
    val reActiveAccountUIState = _reActiveAccountUIState.asStateFlow()

    private val _sendOtpState = MutableStateFlow<Resource<SendOtpResponseModel>>(Resource.loading())
    val sendOtpState = _sendOtpState.asStateFlow()

    fun sendOtp(invalidEmailErrorMsg: String) {
        if (!validateInput(invalidEmailErrorMsg)) return
        _reActiveAccountUIState.value = reActiveAccountUIState.value.copy(isLoading = true)

        coroutineScope.launch{
            val response = sendOtpUseCase(
                SendOtpRequestModel(
                    email = emailInput.value,
                )
            ).toResource()
            _reActiveAccountUIState.value = reActiveAccountUIState.value.copy(isLoading = false)

            _sendOtpState.tryEmit(response)
        }
    }

    private fun validateInput(invalidEmailErrorMsg: String): Boolean {
        _reActiveAccountUIState.value = reActiveAccountUIState.value.copy(emailError = "")
        if (!emailInput.value.isValidEmail()) {
            _reActiveAccountUIState.value = reActiveAccountUIState.value.copy(emailError = invalidEmailErrorMsg)
            return false
        }
        return true
    }

    fun onEmailValueChange(text: String) {
        _emailInput.value = text
        _reActiveAccountUIState.value = reActiveAccountUIState.value.copy(isNullEmail = text.isBlank())
    }
}