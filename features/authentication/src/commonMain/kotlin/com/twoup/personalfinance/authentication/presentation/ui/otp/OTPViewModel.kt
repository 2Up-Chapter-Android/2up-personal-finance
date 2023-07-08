package com.twoup.personalfinance.authentication.presentation.ui.otp

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.ActiveUserResponseModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpRequestModel
import com.twoup.personalfinance.domain.model.authentication.otp.SendOtpResponseModel
import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.remote.util.Resource
import com.twoup.personalfinance.remote.util.toResource
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

    private val _activeUserState = MutableStateFlow<Resource<ActiveUserResponseModel>>(Resource.loading())
    val activeUserState = _activeUserState.asStateFlow()

    private val _sendOtpState = MutableStateFlow<Resource<SendOtpResponseModel>>(Resource.loading())
    val sendOtpState = _sendOtpState.asStateFlow()

    private val _otpUIState = MutableStateFlow(OTPUIState())
    val otpUIState: StateFlow<OTPUIState> get() = _otpUIState

    private val _otpInput = MutableStateFlow("")
    val otpInput: StateFlow<String> get() = _otpInput

    fun onOtpInputChange(otp: String, codeLength: Int) {
        if (otp.length > codeLength) return
        if (otp.any{ !it.isDigit() }) return
        _otpInput.value = otp
        _otpUIState.value = otpUIState.value.copy(isFullFillOtp = otp.length == codeLength)
    }

    fun activeUser(email: String) {
        _otpUIState.value = otpUIState.value.copy(isLoading = true)
        coroutineScope.launch {
            val response = activeUserUseCase(
                ActiveUserRequestModel(
                    email = email,
                    otp = otpInput.value
                )
            ).toResource()
            _otpUIState.value = otpUIState.value.copy(isLoading = false)
            _activeUserState.tryEmit(response)
        }
    }

    fun resendOtp(email: String) {
        _otpUIState.value = otpUIState.value.copy(isLoading = true)

        coroutineScope.launch{
            val response = sendOtpUseCase(
                SendOtpRequestModel(
                    email = email,
                )
            ).toResource()
            _otpUIState.value = otpUIState.value.copy(isLoading = false)

            _sendOtpState.tryEmit(response)
        }
    }

    fun clearStateActiveUser() {
        _activeUserState.value = Resource.loading()
    }

    fun clearStateSendOtp() {
        _sendOtpState.value = Resource.loading()
    }
}



