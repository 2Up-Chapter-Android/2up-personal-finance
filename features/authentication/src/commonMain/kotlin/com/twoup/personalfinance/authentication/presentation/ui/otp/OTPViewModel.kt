package com.twoup.personalfinance.authentication.presentation.ui.otp

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OTPViewModel: ScreenModel, KoinComponent {
    private val sendOtpUseCase: SendOtpUseCase by inject()
    private val activeUserUseCase: ActiveUserUseCase by inject()

    private val _activeUserState = MutableSharedFlow<Result<ActiveUserResponseModel>>()
    val activeUserState = _activeUserState.asSharedFlow()

    private val _sendOtpState = MutableStateFlow<Result<SendOtpResponseModel>>(Result.failure(Exception("Initial Value")))
    val sendOtpState = _sendOtpState.asStateFlow()

    private val _otpUIState = MutableStateFlow(OTPUIState())
    val otpUIState: StateFlow<OTPUIState> get() = _otpUIState

    fun changeOTPFirstTextValue(text: String) {
        _otpUIState.value = otpUIState.value.copy(
            firstText = text
        )
    }

    fun changeOTPSecondTextValue(text: String) {
        _otpUIState.value = otpUIState.value.copy(
            secondText = text
        )
    }

    fun changeOTPThirdTextValue(text: String) {
        _otpUIState.value = otpUIState.value.copy(
            thirdText = text
        )
    }

    fun changeOTPForthTextValue(text: String) {
        _otpUIState.value = otpUIState.value.copy(
            forthText = text
        )
    }

    fun activeUser() {
        _otpUIState.value = otpUIState.value.copy(isLoading = true)
        coroutineScope.launch {
            val response = activeUserUseCase(
                ActiveUserRequestModel(
                    email = "abc@gmail.com",
                    otp = otpUIState.value.firstText + otpUIState.value.secondText + otpUIState.value.thirdText + otpUIState.value.forthText
                )
            )
            _otpUIState.value = otpUIState.value.copy(isLoading = false)
            _activeUserState.tryEmit(response)
        }
    }

    fun resendOtp() {
        _otpUIState.value = otpUIState.value.copy(isLoading = true)

        coroutineScope.launch{
            val response = sendOtpUseCase(
                SendOtpRequestModel(
                    email = "abc@gmail.com",
                )
            )
            _otpUIState.value = otpUIState.value.copy(isLoading = false)

            _sendOtpState.tryEmit(response)
        }
    }

    fun clearStateOTP() {
        _otpUIState.value = otpUIState.value.copy(isLoading = true)
    }
}



