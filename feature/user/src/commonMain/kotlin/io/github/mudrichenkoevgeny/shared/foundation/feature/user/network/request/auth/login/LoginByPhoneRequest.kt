package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for user authentication using a phone number and a confirmation code.
 *
 * @property phoneNumber The user's phone number.
 * @property confirmationCode The one-time code for verification.
 */
@Serializable
public data class LoginByPhoneRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.PHONE_NUMBER)
    public val phoneNumber: String,
    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    public val confirmationCode: String
)
