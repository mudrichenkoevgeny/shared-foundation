package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for unlocking a temporarily locked account using an email confirmation code.
 *
 * @property email The email address associated with the locked account.
 * @property confirmationCode The verification code sent to the user's email.
 */
@Serializable
data class UnlockByEmailConfirmationRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    val confirmationCode: String
)
