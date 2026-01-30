package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for linking a phone number identifier to a user account.
 *
 * @property phoneNumber The phone number to be associated with the account.
 * @property confirmationCode The verification code sent to the phone number to confirm ownership.
 */
@Serializable
data class AddUserIdentifierPhoneRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.PHONE_NUMBER)
    val phoneNumber: String,

    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    val confirmationCode: String
)