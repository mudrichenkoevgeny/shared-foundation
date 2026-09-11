package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for unlocking a temporarily locked account using an SMS/phone confirmation code.
 *
 * @property phoneNumber The phone number associated with the locked account.
 * @property confirmationCode The verification code sent to the user's phone.
 */
@Serializable
data class UnlockByPhoneConfirmationRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.PHONE_NUMBER)
    val phoneNumber: String,

    @NotBlankStringField
    @SerialName(UserApiFields.CONFIRMATION_CODE)
    val confirmationCode: String
)
