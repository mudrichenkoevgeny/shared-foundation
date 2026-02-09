package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for adding email identifier to an existing user account.
 *
 * @property email The email address to be associated with the account.
 * @property password The password to be set for this email identifier.
 * @property confirmationCode The verification code sent to the email to confirm ownership.
 */
@Serializable
data class AddUserIdentifierEmailRequest(
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