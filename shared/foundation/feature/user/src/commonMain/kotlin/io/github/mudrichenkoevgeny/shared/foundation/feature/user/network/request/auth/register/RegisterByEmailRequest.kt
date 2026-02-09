package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for new user registration using email and a verification code.
 *
 * @property email The email address to be associated with the new account.
 * @property password The password chosen by the user for the new account.
 * @property confirmationCode The verification code sent to the email to confirm ownership.
 */
@Serializable
data class RegisterByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    val password: String,

    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    val confirmationCode: String
)