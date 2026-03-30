package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation.SendConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the response after requesting a confirmation (OTP) code.
 *
 * @property retryAfterSeconds The duration in seconds that the user must wait before requesting a new code.
 * @property numberOfSymbols The number of characters the user must enter for the confirmation code.
 */
@Serializable
data class SendConfirmationPayload(
    @SerialName(UserApiFields.RETRY_AFTER_SECONDS)
    val retryAfterSeconds: Int,

    @SerialName(UserApiFields.NUMBER_OF_SYMBOLS)
    val numberOfSymbols: Int
)