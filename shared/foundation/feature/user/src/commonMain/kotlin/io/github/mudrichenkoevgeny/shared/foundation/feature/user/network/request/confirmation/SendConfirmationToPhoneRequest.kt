package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for requesting a confirmation code to be sent to a user's phone number.
 *
 * @property phoneNumber The phone number where the confirmation code will be delivered.
 */
@Serializable
data class SendConfirmationToPhoneRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.PHONE_NUMBER)
    val phoneNumber: String
)