package com.twoup.personalfinance.authentication.presentation.ui.otp

import PersonalFinance.features.authentication.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.authentication.presentation.theme.cornerRadius_reActiveAccount_button
import com.twoup.personalfinance.authentication.presentation.theme.height_reActiveAccount_button
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_reActiveAccount_button
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_reActiveAccount_emailInput
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_reActiveAccount_subtitle
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_reActiveAccount_title
import com.twoup.personalfinance.authentication.presentation.theme.paddingHorizontal_reActiveAccount_parentView
import com.twoup.personalfinance.authentication.presentation.theme.progressBarStrokeWidth_login
import com.twoup.personalfinance.authentication.presentation.theme.size_login_progressBar
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_loginButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_reActiveAccount_subtitle
import com.twoup.personalfinance.authentication.presentation.theme.textSize_reActiveAccount_title
import com.twoup.personalfinance.authentication.presentation.ui.login.LoginEditText
import com.twoup.personalfinance.authentication.presentation.ui.login.LoginErrorText
import com.twoup.personalfinance.navigation.AuthenticationSharedScreen
import com.twoup.personalfinance.utils.data.HttpException
import com.twoup.personalfinance.utils.data.NetworkException
import com.twoup.personalfinance.utils.data.fold
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier

class ReActiveAccountScreen : Screen {
    @Composable
    override fun Content() {
        ReActiveAccountScreen()
    }

    @Composable
    fun ReActiveAccountScreen() {
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val viewModel = rememberScreenModel { ReActiveAccountViewModel() }
        val emailInput = viewModel.emailInput.collectAsState()
        val sendOtpState = viewModel.sendOtpState.collectAsState()
        val reActiveAccountUIState = viewModel.reActiveAccountUIState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val otpScreen = rememberScreen(AuthenticationSharedScreen.OTPScreen(emailInput.value))

        LaunchedEffect(key1 = sendOtpState.value) {
            sendOtpState.value.fold(onSuccess = {
                navigator.replace(otpScreen)
            }, onFailure = {
                when (it) {
                    is HttpException -> Napier.d(
                        tag = "TestReActiveAccount", message = it.errorMessage.toString()
                    )

                    is NetworkException -> Napier.d(
                        tag = "TestReActiveAccount", message = "Mat mang roi"
                    )

                    else -> Napier.d(
                        tag = "TestReActiveAccount", message = it.message.toString()
                    )
                }
            })
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = paddingHorizontal_reActiveAccount_parentView).clickable(
                    interactionSource = interactionSource, indication = null
                ) { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(marginTop_reActiveAccount_title))
            Text(
                text = MR.strings.reActiveAccount_title.desc().localized(),
                fontSize = textSize_reActiveAccount_title,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(marginTop_reActiveAccount_subtitle))
            Text(
                text = MR.strings.reActiveAccount_subTitle.desc().localized(),
                fontSize = textSize_reActiveAccount_subtitle,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(marginTop_reActiveAccount_emailInput))
            LoginEditText(
                text = emailInput.value,
                onTextChange = { viewModel.onEmailValueChange(it) },
                keyboardOption = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                ),
                trailingIcon = {
                    IconButton(onClick = { viewModel.onEmailValueChange("") }) {
                        Icon(
                            painter = painterResource(MR.images.ic_clear),
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
                hint = MR.strings.all_email.desc().localized()
            )
            AnimatedVisibility(visible = reActiveAccountUIState.value.visibilityEmailError) {
                LoginErrorText(
                    text = reActiveAccountUIState.value.emailError
                )
            }

            Spacer(modifier = Modifier.height(marginTop_reActiveAccount_button))

            val accountErrorMsg = MR.strings.invalid_email_error.desc().localized()
            Button(
                onClick = {
                    viewModel.sendOtp(accountErrorMsg)
                    focusManager.clearFocus()
                },
                modifier = Modifier.fillMaxWidth().height(height_reActiveAccount_button),
                shape = RoundedCornerShape(cornerRadius_reActiveAccount_button),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(MR.colors.login_loginButton),
                    disabledBackgroundColor = colorResource(MR.colors.login_loginButton_disable)
                ),
                enabled = reActiveAccountUIState.value.enableSendButton
            ) {
                Text(
                    text = MR.strings.all_continue.desc().localized(),
                    fontSize = textSize_login_loginButton,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        AnimatedVisibility(
            visible = reActiveAccountUIState.value.isLoading, enter = fadeIn(), exit = fadeOut()
        ) {
            Surface(color = Color.Transparent) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray.copy(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size_login_progressBar),
                        color = colorResource(MR.colors.login_progressBar),
                        strokeWidth = progressBarStrokeWidth_login
                    )
                }
            }
        }
    }
}