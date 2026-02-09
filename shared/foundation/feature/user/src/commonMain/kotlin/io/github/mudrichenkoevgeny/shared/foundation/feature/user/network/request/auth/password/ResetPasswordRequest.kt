package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for the password reset process after identity verification.
 *
 * @property email The email address associated with the account.
 * @property confirmationCode The verification code sent to the user's email.
 * @property newPassword The new password to be set for the account.
 */
@Serializable
data class ResetPasswordRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    val confirmationCode: String,

    @NotBlankStringField
    @SerialName(UserApiFields.NEW_PASSWORD)
    val newPassword: String
)