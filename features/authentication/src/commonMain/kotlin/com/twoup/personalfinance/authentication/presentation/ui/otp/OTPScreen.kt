package com.twoup.personalfinance.authentication.presentation.ui.otp

import PersonalFinance.features.authentication.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.authentication.presentation.theme.height_otp_imageOTP
import com.twoup.personalfinance.authentication.presentation.theme.height_otp_otpInputIndicator
import com.twoup.personalfinance.authentication.presentation.theme.height_otp_textField
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_otp_errorText
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_otp_activeButton
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_errorText
import com.twoup.personalfinance.authentication.presentation.theme.paddingTop_otp_resendOTP
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_otp_otpInput
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_otp_titleImage
import com.twoup.personalfinance.authentication.presentation.theme.padding_otp_otpInputIndicator
import com.twoup.personalfinance.authentication.presentation.theme.padding_otp_parentView
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_checkEmail
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_resendOTP
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_textButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_otpInput
import com.twoup.personalfinance.authentication.presentation.theme.textSize_otp_title
import com.twoup.personalfinance.authentication.presentation.theme.width_otp_imageOTP
import com.twoup.personalfinance.authentication.presentation.theme.width_otp_textField
import com.twoup.personalfinance.navigation.AuthenticationSharedScreen
import com.twoup.personalfinance.remote.util.HttpException
import com.twoup.personalfinance.remote.util.fold
import com.twoup.personalfinance.utils.CountDownTimer
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.desc

data class OTPScreen(private val email: String) : Screen {
    @Composable
    override fun Content() {
        OTPScreen()
    }

    @Composable
    fun OTPScreen() {
        val interactionSource = remember { MutableInteractionSource() }
        val focusManager = LocalFocusManager.current
        val otpViewModel: OTPViewModel = rememberScreenModel { OTPViewModel() }
        val otpUIState = otpViewModel.otpUIState.collectAsState()
        val otpInput = otpViewModel.otpInput.collectAsState()
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

        Column(modifier = Modifier
            .padding(padding_otp_parentView)
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) { focusManager.clearFocus() }
        ) {
            Text(
                text = MR.strings.otp_title.desc().localized(),
                fontWeight = FontWeight.Bold,
                fontSize = textSize_otp_title,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = buildAnnotatedString {
                    append(MR.strings.otp_remindCheckMail.desc().localized())
                    append(" ")
                    withStyle(SpanStyle(color = colorResource(MR.colors.otp_email))) {
                        append(email)
                    }
                },
                fontSize = textSize_otp_checkEmail,
            )

            Spacer(modifier = Modifier.height(marginTop_otp_titleImage))
            Image(
                painter = painterResource(MR.images.logo_otp),
                contentDescription = "My Image",
                modifier = Modifier
                    .width(width_otp_imageOTP)
                    .height(height_otp_imageOTP)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(marginTop_otp_otpInput))
            OTPInput(codeLength = 4, initialCode = otpInput.value){ otpViewModel.onOtpInputChange(it, 4) }

            AnimatedVisibility(
                visible = isVisibleResendButton.value,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                Spacer(modifier = Modifier.height(paddingTop_otp_resendOTP))
                Text(
                    text = buildAnnotatedString {
                        append(MR.strings.otp_resendOtp_message.desc().localized())
                        append(" ")
                        withStyle(SpanStyle(color = colorResource(MR.colors.all_textButton))) {
                            append(MR.strings.otp_resendOTP_resendButton.desc().localized())
                        }
                    },
                    color = Color.Black,
                    fontSize = textSize_otp_resendOTP,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        otpViewModel.resendOtp(email)
                        isVisibleResendButton.value = false
                        countDownTimer.start()
                    })
            }

            Spacer(modifier = Modifier.height(marginTop_otp_activeButton))
            Button(
                onClick = {
                    otpViewModel.activeUser(email)
                    focusManager.clearFocus()
                    countDownTimer.cancel()
                },
                enabled = otpUIState.value.enableActiveButton,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(MR.colors.otp_activeButton),
                    disabledBackgroundColor = colorResource(MR.colors.otp_activeButton_disable),
                )
            ) {
                Text(
                    text = MR.strings.otp_active.desc().localized(),
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize_otp_textButton,
                    color = Color.White
                )
            }
        }

        LaunchedEffect(activeUserState.value) {
            activeUserState.value.fold(
                onSuccess = {
                    otpViewModel.clearStateActiveUser()
                    navigator.popUntil { it == loginScreen }
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
                                navigator.popUntil { screen -> screen == loginScreen }
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
                                navigator.popUntil { screen -> screen == loginScreen }
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
    fun OTPInput(codeLength: Int, initialCode: String, onTextChanged: (String) -> Unit) {
        val code = remember(initialCode) {
            mutableStateOf(TextFieldValue(initialCode, TextRange(initialCode.length)))
        }
        val focusRequester = FocusRequester()
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            BasicTextField(
                value = code.value,
                onValueChange = {
                    onTextChanged(it.text)
                },
                modifier = Modifier.focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                decorationBox = {
                    CodeInputDecoration(code.value.text, codeLength)
                },
                cursorBrush = SolidColor(Color.Black)
            )
        }
    }

    @Composable
    private fun CodeInputDecoration(code: String, length: Int) {
        Box(modifier = Modifier) {
            Row(horizontalArrangement = Arrangement.Center) {
                for (i in 0 until length) {
                    val text = if (i < code.length) code[i].toString() else ""
                    CodeEntry(text)
                }
            }
        }
    }

    @Composable
    private fun CodeEntry(text: String) {
        Box(
            modifier = Modifier
                .width(width_otp_textField)
                .height(height_otp_textField),
            contentAlignment = Alignment.Center
        ) {
            val color = animateColorAsState(
                targetValue = if (text.isEmpty()) Color.Gray.copy(alpha = .8f)
                else Color.Blue.copy(.8f)
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontSize = textSize_otp_otpInput,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(padding_otp_otpInputIndicator)
                    .height(height_otp_otpInputIndicator)
                    .fillMaxWidth()
                    .background(color.value)
            )
        }
    }
}