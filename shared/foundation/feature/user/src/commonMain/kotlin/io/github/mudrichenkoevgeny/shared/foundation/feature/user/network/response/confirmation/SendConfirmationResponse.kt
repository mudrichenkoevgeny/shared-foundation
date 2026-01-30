package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the response after requesting a confirmation code.
 *
 * @property retryAfterSeconds The duration in seconds that the user must wait before requesting a new code.
 */
@Serializable
data class SendConfirmationResponse(
    @SerialName(UserApiFields.RETRY_AFTER_SECONDS)
    val retryAfterSeconds: Int
)