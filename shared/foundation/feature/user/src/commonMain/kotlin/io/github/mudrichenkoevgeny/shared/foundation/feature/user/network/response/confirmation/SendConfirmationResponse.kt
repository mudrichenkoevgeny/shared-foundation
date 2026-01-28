package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendConfirmationResponse(
    @SerialName(UserApiFields.RETRY_AFTER_SECONDS)
    val retryAfterSeconds: Int
)