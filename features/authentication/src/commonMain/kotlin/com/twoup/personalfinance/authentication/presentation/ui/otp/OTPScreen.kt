package com.twoup.personalfinance.authentication.presentation.ui.otp

import PersonalFinance.features.authentication.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.authentication.presentation.theme.height_otp_imageOTP
import com.twoup.personalfinance.authentication.presentation.theme.height_otp_textField
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_otp_errorText
import com.twoup.personalfinance.authentication.presentation.theme.paddingStart_otp_textField
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_button
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_checkEmail
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_email
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_errorText
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_imageOTP
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_resendOTP
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_textField
import com.twoup.personalfinance.authentication.presentation.theme.paddingVertical_otp_textButton
import com.twoup.personalfinance.authentication.presentation.theme.padding_otp_parentView
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_checkEmail
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_email
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_resendOTP
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_textButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_title
import com.twoup.personalfinance.authentication.presentation.theme.width_otp_imageOTP
import com.twoup.personalfinance.authentication.presentation.theme.width_otp_textField
import com.twoup.personalfinance.navigation.AuthenticationSharedScreen
import com.twoup.personalfinance.remote.util.HttpException
import com.twoup.personalfinance.remote.util.fold
import com.twoup.personalfinance.utils.CountDownTimer
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource

import dev.icerock.moko.resources.desc.desc

class OTPScreen : Screen {
    @Composable
    override fun Content() {
        OTPScreen()
    }

    @Composable
    fun OTPScreen() {
        val otpViewModel: OTPViewModel = rememberScreenModel { OTPViewModel() }
        val otpUIState = otpViewModel.otpUIState.collectAsState()
        val activeUserState = otpViewModel.activeUserState.collectAsState()
        val sendOtpState = otpViewModel.sendOtpState.collectAsState()
        val isVisibleResendButton = remember {
            mutableStateOf(false)
        }
        val countDownTimer = remember {
            object : CountDownTimer(61_000, 1000) {

                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    isVisibleResendButton.value = true
                }
            }
        }
        val navigator = LocalNavigator.currentOrThrow
        val loginScreen = rememberScreen(AuthenticationSharedScreen.LoginScreen)

        LaunchedEffect(key1 = Unit) {
            countDownTimer.start()
        }

        Column(
            modifier = Modifier
                .padding(padding_otp_parentView)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Text(
                    MR.strings.otp_title.desc().localized(),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = textSize_otp_title
                    )
                )
                Text(
                    MR.strings.otp_enterInput.desc().localized(),
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = textSize_otp_checkEmail
                    ),
                    modifier = Modifier.padding(top = paddingTop_otp_checkEmail)
                )

                Image(
                    painter = painterResource(MR.images.logo_otp),
                    contentDescription = "My Image",
                    modifier = Modifier
                        .width(
                            width_otp_imageOTP
                        )
                        .height(
                            height_otp_imageOTP
                        )
                        .padding(
                            top = paddingTop_otp_imageOTP
                        )
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "abc@gmail.com",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingTop_otp_email)
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold, fontSize = textSize_otp_email

                    )
                )

                val listenRequestFirstTextField = remember { FocusRequester() }
                val listenRequestSecondTextField = remember { FocusRequester() }
                val listenRequestThirdTextField = remember { FocusRequester() }
                val listenRequestForthTextField = remember { FocusRequester() }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = paddingTop_otp_textField
                        ), horizontalArrangement = Arrangement.Center
                ) {
                    TextFieldEnterOTP(
                        value = otpUIState.value.firstText,
                        onValueChange = { otpViewModel.changeOTPFirstTextValue(it) },
                        focusRequester = listenRequestFirstTextField,
                        nextFocusRequester = listenRequestSecondTextField
                    )
                    TextFieldEnterOTP(
                        value = otpUIState.value.secondText,
                        onValueChange = { otpViewModel.changeOTPSecondTextValue(it) },
                        focusRequester = listenRequestSecondTextField,
                        nextFocusRequester = listenRequestThirdTextField
                    )
                    TextFieldEnterOTP(
                        value = otpUIState.value.thirdText,
                        onValueChange = { otpViewModel.changeOTPThirdTextValue(it) },
                        focusRequester = listenRequestThirdTextField,
                        nextFocusRequester = listenRequestForthTextField
                    )
                    TextFieldEnterOTP(
                        value = otpUIState.value.forthText,
                        onValueChange = { otpViewModel.changeOTPForthTextValue(it) },
                        focusRequester = listenRequestForthTextField,
                        nextFocusRequester = listenRequestForthTextField,
                    )
                }

                AnimatedVisibility(visible = isVisibleResendButton.value) {
                    Text(MR.strings.otp_resendOTP.desc().localized(),
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Magenta,
                            fontSize = textSize_otp_resendOTP
                        ),
                        modifier = Modifier
                            .padding(top = paddingTop_otp_resendOTP)
                            .clickable {
                                otpViewModel.resendOtp()
                                isVisibleResendButton.value = false
                                countDownTimer.start()
                            })
                }
                Button(
                    onClick = {
                        otpViewModel.activeUser()
                        countDownTimer.cancel()
                    },
                    enabled = otpUIState.value.enableActiveButton,

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = paddingTop_otp_button
                        ),
                ) {
                    Text(
                        MR.strings.otp_active.desc().localized(), modifier = Modifier.padding(
                            vertical = paddingVertical_otp_textButton
                        ), style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold, fontSize = textSize_otp_textButton
                        )
                    )
                }
            }
        }

        LaunchedEffect(activeUserState.value) {
            activeUserState.value.fold(
                onSuccess = {
                    otpViewModel.clearStateActiveUser()
                    navigator.push(loginScreen)
                },
                onFailure = {
                    if (it is HttpException && it.errorCode != null) {
                        when (it.errorCode) {
                            "org.up.finance.exception.OtpNotFoundException" -> {
                                isVisibleResendButton.value = true
                            }

                            "org.up.finance.exception.OtpBadRequestException" -> {
                            }

                            "org.up.finance.exception.EmailNotFoundException" -> {
                            }

                            "org.up.finance.exception.UserActivatedException" -> {
                                otpViewModel.clearStateActiveUser()
                                navigator.push(loginScreen)
                            }

                            "org.up.finance.exception.xxx.MethodArgumentNotValidException" -> {
                            }
                        }
                    }
                }
            )
        }

        LaunchedEffect(sendOtpState.value) {
            sendOtpState.value.fold(
                onSuccess = {
                    otpViewModel.clearStateSendOtp()
                },
                onFailure = {
                    if (it is HttpException && it.errorMessage != null) {
                        when (it.errorMessage) {
                            "org.up.finance.exception.UserActivatedException" -> {
                                otpViewModel.clearStateSendOtp()
                                navigator.push(loginScreen)
                            }

                            "org.up.finance.exception.EmailNotFoundException" -> {
                            }

                            "org.up.finance.exception.xxx.MethodArgumentNotValidException" -> {
                            }
                        }
                    }
                }
            )
        }
    }

    @Composable
    fun ErrorText(text: String) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .height(marginTop_otp_errorText)
                    .padding(top = paddingTop_otp_errorText)
            )

            Text(
                text = text, color = Color.Red
            )
        }
    }

    @Composable
    fun TextFieldEnterOTP(
        value: String,
        onValueChange: (String) -> Unit,
        focusRequester: FocusRequester,
        nextFocusRequester: FocusRequester,
    ) {
        TextField(
            value = value, onValueChange = { it: String ->
                if (it.length <= 1) {
                    if (it.all { it.isDigit() }) {
                        onValueChange(it)
                    }
                }
            }, modifier = Modifier
                .padding(
                    start = paddingStart_otp_textField
                )
                .width(
                    width_otp_textField
                )
                .height(height_otp_textField)

                .focusRequester(focusRequester), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ), singleLine = true, textStyle = TextStyle(fontWeight = FontWeight.Bold)
        )
        LaunchedEffect(value) {
            if (value.length == 1) {
                nextFocusRequester.requestFocus()
            }
        }
    }
}