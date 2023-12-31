package com.twoup.personalfinance.authentication.presentation.ui.login

import PersonalFinance.features.authentication.MR
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.authentication.presentation.theme.cornerRadius_login_loginButton
import com.twoup.personalfinance.authentication.presentation.theme.cornerRadius_login_loginTextField
import com.twoup.personalfinance.authentication.presentation.theme.height_login_loginButton
import com.twoup.personalfinance.authentication.presentation.theme.height_login_welcomeImage
import com.twoup.personalfinance.authentication.presentation.theme.marginBottom_login_parentView
import com.twoup.personalfinance.authentication.presentation.theme.marginBottom_login_registerTextButton
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_login_forgotPassTextButton
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_login_loginButton
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_login_loginErrorText
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_login_passwordTextField
import com.twoup.personalfinance.authentication.presentation.theme.marginTop_login_usernameTextField
import com.twoup.personalfinance.authentication.presentation.theme.paddingHorizontal_login_parentView
import com.twoup.personalfinance.authentication.presentation.theme.paddingStart_login_loginErrorText
import com.twoup.personalfinance.authentication.presentation.theme.paddingVertical_login_welcomeImage
import com.twoup.personalfinance.authentication.presentation.theme.progressBarStrokeWidth_login
import com.twoup.personalfinance.authentication.presentation.theme.size_login_progressBar
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_forgotPasswordTextButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_loginButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_loginErrorText
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_loginTextField
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_registerTextButton
import com.twoup.personalfinance.authentication.presentation.theme.textSize_login_welcomeTitle
import com.twoup.personalfinance.authentication.presentation.theme.width_login_welcomeImage
import com.twoup.personalfinance.navigation.AuthenticationSharedScreen
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.utils.data.HttpException
import com.twoup.personalfinance.utils.data.NetworkException
import com.twoup.personalfinance.utils.data.fold
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        LoginScreen()
    }

    @Composable
    fun LoginScreen() {
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val viewModel = rememberScreenModel { LoginViewModel() }
        val usernameInput = viewModel.usernameInput.collectAsState()
        val passwordInput = viewModel.passwordInput.collectAsState()
        val loginState = viewModel.loginState.collectAsState()
        val loginUIState = viewModel.loginUiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val registerScreen = rememberScreen(AuthenticationSharedScreen.RegisterScreen)
        val reActiveAccScreen = rememberScreen(AuthenticationSharedScreen.ReActiveAccountScreen)
//        val dashBoardScreen = rememberScreen(TransactionSharedScreen.TransactionDashboardScreen)
//        val mainScreen = rememberScreen(MainScreenSharedScreen.MainScreen())

        LaunchedEffect(key1 = loginState.value) {
            loginState.value.fold(
                onSuccess = {
                    Napier.d(tag = "TestLogin", message = it.data.accessToken)
//                    navigator.replaceAll(mainScreen)
                },
                onFailure = {
                    when (it) {
                        is HttpException -> Napier.d(
                            tag = "TestLogin", message = it.errorMessage.toString()
                        )

                        is NetworkException -> Napier.d(
                            tag = "TestLogin", message = "Mat mang roi"
                        )

                        else -> Napier.d(
                            tag = "TestLogin", message = it.message.toString()
                        )
                    }
                }
            )
        }

        Box {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = paddingHorizontal_login_parentView).clickable(
                        interactionSource = interactionSource, indication = null
                    ) { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(MR.images.image_welcome_login),
                        contentDescription = "",
                        modifier = Modifier.width(width_login_welcomeImage)
                            .height(height_login_welcomeImage)
                            .padding(vertical = paddingVertical_login_welcomeImage),
                    )

                    Text(
                        text = MR.strings.login_welcomeTitle.desc().localized(),
                        fontSize = textSize_login_welcomeTitle,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(marginTop_login_usernameTextField))
                    LoginEditText(
                        text = usernameInput.value,
                        onTextChange = { viewModel.onUsernameValueChange(it) },
                        keyboardOption = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                        ),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.onUsernameValueChange("") }) {
                                Icon(
                                    painter = painterResource(MR.images.ic_clear),
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }
                        },
                        hint = MR.strings.login_hint_username.desc().localized()
                    )
                    AnimatedVisibility(visible = loginUIState.value.visibilityUsernameError) {
                        LoginErrorText(
                            text = loginUIState.value.usernameError
                        )
                    }

                    Spacer(modifier = Modifier.height(marginTop_login_passwordTextField))
                    LoginPasswordEditText(
                        text = passwordInput.value,
                        onTextChange = { viewModel.onPasswordValueChange(it) },
                        imeAction = ImeAction.Done
                    )
                    AnimatedVisibility(visible = loginUIState.value.visibilityPasswordError) {
                        LoginErrorText(
                            text = loginUIState.value.passwordError
                        )
                    }

                    Spacer(modifier = Modifier.height(marginTop_login_forgotPassTextButton))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                        Text(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                Napier.d(tag = "TestLogin", message = "forgot pass")
                            },
                            text = MR.strings.login_forgotPassword.desc().localized(),
                            fontSize = textSize_login_forgotPasswordTextButton,
                            color = colorResource(MR.colors.all_textButton),
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.height(marginTop_login_loginButton))

                    val accountErrorMsg = MR.strings.invalid_email_error.desc().localized()
                    val passwordErrorMsg =
                        MR.strings.login_error_incorrectPassword.desc().localized()
                    Button(
                        onClick = {
                            viewModel.login(accountErrorMsg, passwordErrorMsg)
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.fillMaxWidth().height(height_login_loginButton),
                        shape = RoundedCornerShape(cornerRadius_login_loginButton),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(MR.colors.login_loginButton),
                            disabledBackgroundColor = colorResource(MR.colors.login_loginButton_disable)
                        ),
                        enabled = loginUIState.value.enableLoginButton
                    ) {
                        Text(
                            text = MR.strings.all_login.desc().localized(),
                            fontSize = textSize_login_loginButton,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Column {
                    Text(text = buildAnnotatedString {
                        append(MR.strings.login_dontHaveAccount.desc().localized())
                        append(" ")
                        withStyle(SpanStyle(color = colorResource(MR.colors.all_textButton))) {
                            append(MR.strings.all_signup.desc().localized())
                        }
                    },
                        fontSize = textSize_login_registerTextButton,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            navigator.push(registerScreen)
                        })

                    Spacer(modifier = Modifier.height(marginBottom_login_registerTextButton))
                    Text(text = buildAnnotatedString {
                        append(MR.strings.login_haveNotActivated.desc().localized())
                        append(" ")
                        withStyle(SpanStyle(color = colorResource(MR.colors.all_textButton))) {
                            append(MR.strings.login_textButtonActive.desc().localized())
                        }
                    },
                        fontSize = textSize_login_registerTextButton,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            navigator.push(reActiveAccScreen)
                        })
                    Spacer(modifier = Modifier.height(marginBottom_login_parentView))
                }
            }
        }
        AnimatedVisibility(
            visible = loginUIState.value.isLoading, enter = fadeIn(), exit = fadeOut()
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

    @Composable
    fun LoginPasswordEditText(
        text: String,
        onTextChange: (String) -> Unit,
        imeAction: ImeAction,
    ) {
        val passwordVisibility = remember { mutableStateOf(false) }

        val icon = if (passwordVisibility.value) painterResource(MR.images.ic_hide_password)
        else painterResource(MR.images.ic_show_password)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                topEnd = cornerRadius_login_loginTextField,
                bottomStart = cornerRadius_login_loginTextField
            ),
            value = text,
            onValueChange = { onTextChange(it) },
            label = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = MR.strings.login_hint_password.desc().localized(),
                    color = Color.Black,
                    fontSize = textSize_login_loginTextField
                )
            },
            textStyle = TextStyle(fontSize = textSize_login_loginTextField),
            singleLine = true,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(
                            painter = icon,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = imeAction
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black
            ),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(mask = '*')
        )

    }
}

@Composable
fun LoginEditText(
    text: String,
    onTextChange: (String) -> Unit,
    keyboardOption: KeyboardOptions,
    trailingIcon: @Composable () -> Unit,
    hint: String
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topEnd = cornerRadius_login_loginTextField,
            bottomStart = cornerRadius_login_loginTextField
        ),
        value = text,
        onValueChange = { onTextChange(it) },
        label = {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = hint,
                color = Color.Black,
                fontSize = textSize_login_loginTextField
            )
        },
        textStyle = TextStyle(fontSize = textSize_login_loginTextField),
        singleLine = true,
        trailingIcon = {
            if (text.isNotBlank()) trailingIcon()
        },
        keyboardOptions = keyboardOption,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black
        )
    )
}


@Composable
fun LoginErrorText(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = paddingStart_login_loginErrorText)
    ) {
        Spacer(modifier = Modifier.height(marginTop_login_loginErrorText))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = textSize_login_loginErrorText,
            color = colorResource(MR.colors.login_errorText)
        )
    }
}