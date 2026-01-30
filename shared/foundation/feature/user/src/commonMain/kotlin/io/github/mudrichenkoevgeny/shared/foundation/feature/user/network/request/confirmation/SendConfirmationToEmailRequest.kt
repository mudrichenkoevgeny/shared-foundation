package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for requesting a confirmation code to be sent to a user's email address.
 *
 * @property email The email address where the confirmation code will be delivered.
 */
@Serializable
data class SendConfirmationToEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String
)