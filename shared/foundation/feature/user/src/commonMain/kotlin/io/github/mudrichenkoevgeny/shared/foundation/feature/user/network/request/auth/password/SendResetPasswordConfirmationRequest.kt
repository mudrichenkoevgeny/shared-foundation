package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for requesting a password reset confirmation code to be sent to the user's email.
 *
 * @property email The email address where the confirmation code will be delivered.
 */
@Serializable
data class SendResetPasswordConfirmationRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String
)