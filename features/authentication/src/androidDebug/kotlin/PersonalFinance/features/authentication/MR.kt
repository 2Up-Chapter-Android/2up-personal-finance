package PersonalFinance.features.authentication

import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource

public actual object MR {
    public actual object strings : ResourceContainer<StringResource> {
        actual val all_login: StringResource
            get() = TODO("Not yet implemented")
        actual val all_signup: StringResource
            get() = TODO("Not yet implemented")
        actual val all_username: StringResource
            get() = TODO("Not yet implemented")
        actual val all_full_name: StringResource
            get() = TODO("Not yet implemented")
        actual val all_email: StringResource
            get() = TODO("Not yet implemented")
        actual val all_password: StringResource
            get() = TODO("Not yet implemented")
        actual val all_confirm_password: StringResource
            get() = TODO("Not yet implemented")
        actual val all_continue: StringResource
            get() = TODO("Not yet implemented")
        actual val invalid_email_error: StringResource
            get() = TODO("Not yet implemented")
        actual val login_welcomeTitle: StringResource
            get() = TODO("Not yet implemented")
        actual val login_hint_username: StringResource
            get() = TODO("Not yet implemented")
        actual val login_hint_password: StringResource
            get() = TODO("Not yet implemented")
        actual val login_error_notExistAccount: StringResource
            get() = TODO("Not yet implemented")
        actual val login_error_incorrectPassword: StringResource
            get() = TODO("Not yet implemented")
        actual val login_forgotPassword: StringResource
            get() = TODO("Not yet implemented")
        actual val login_dontHaveAccount: StringResource
            get() = TODO("Not yet implemented")
        actual val login_haveNotActivated: StringResource
            get() = TODO("Not yet implemented")
        actual val login_textButtonActive: StringResource
            get() = TODO("Not yet implemented")
        actual val register_title_two: StringResource
            get() = TODO("Not yet implemented")
        actual val register_already_have_account: StringResource
            get() = TODO("Not yet implemented")
        actual val register_clear: StringResource
            get() = TODO("Not yet implemented")
        actual val register_show_password: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hide_password: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hint_username: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hint_full_name: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hint_email: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hint_password: StringResource
            get() = TODO("Not yet implemented")
        actual val register_hint_confirm_password: StringResource
            get() = TODO("Not yet implemented")
        actual val register_error_invalid_username: StringResource
            get() = TODO("Not yet implemented")
        actual val register_error_invalid_full_name: StringResource
            get() = TODO("Not yet implemented")
        actual val register_error_invalid_password: StringResource
            get() = TODO("Not yet implemented")
        actual val register_error_invalid_confirm_password: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_title: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_remindCheckMail: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_resendOtp_message: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_resendOTP_resendButton: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_expired: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_activated: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_format: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_active: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_fillOtp: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_email_existed: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_error_fillEmail: StringResource
            get() = TODO("Not yet implemented")
        actual val otp_hint_email: StringResource
            get() = TODO("Not yet implemented")
        actual val reActiveAccount_title: StringResource
            get() = TODO("Not yet implemented")
        actual val reActiveAccount_subTitle: StringResource
            get() = TODO("Not yet implemented")

    }

    public actual object plurals : ResourceContainer<PluralsResource>
    public actual object images : ResourceContainer<ImageResource> {
        actual val ic_clear: ImageResource
            get() = TODO("Not yet implemented")
        actual val ic_hide_password: ImageResource
            get() = TODO("Not yet implemented")
        actual val ic_show_password: ImageResource
            get() = TODO("Not yet implemented")
        actual val image_welcome_login: ImageResource
            get() = TODO("Not yet implemented")
        actual val logo_otp: ImageResource
            get() = TODO("Not yet implemented")

    }

    public actual object fonts : ResourceContainer<FontResource>
    public actual object files : ResourceContainer<FileResource>
    public actual object colors : ResourceContainer<ColorResource> {
        actual val all_textButton: ColorResource
            get() = TODO("Not yet implemented")
        actual val login_loginButton: ColorResource
            get() = TODO("Not yet implemented")
        actual val login_loginButton_disable: ColorResource
            get() = TODO("Not yet implemented")
        actual val login_progressBar: ColorResource
            get() = TODO("Not yet implemented")
        actual val login_errorText: ColorResource
            get() = TODO("Not yet implemented")
        actual val canvas_drawCircle_register: ColorResource
            get() = TODO("Not yet implemented")
        actual val canvas_drawRect_register: ColorResource
            get() = TODO("Not yet implemented")
        actual val float_button_register: ColorResource
            get() = TODO("Not yet implemented")
        actual val float_button_register_disable: ColorResource
            get() = TODO("Not yet implemented")
        actual val otp_email: ColorResource
            get() = TODO("Not yet implemented")
        actual val otp_activeButton: ColorResource
            get() = TODO("Not yet implemented")
        actual val otp_activeButton_disable: ColorResource
            get() = TODO("Not yet implemented")

    }

    public actual object assets : ResourceContainer<AssetResource>

}