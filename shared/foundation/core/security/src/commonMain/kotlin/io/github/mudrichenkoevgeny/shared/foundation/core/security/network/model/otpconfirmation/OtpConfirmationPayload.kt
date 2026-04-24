package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data transfer object representing OTP configuration settings.
 *
 * @property retryAfterSeconds The cooling-off period in seconds before a new OTP request is allowed.
 * @property numberOfSymbols The length of the generated OTP code.
 * @property expirationSeconds The time-to-live (TTL) for the code in seconds.
 */
@Serializable
data class OtpConfirmationPayload(
    @SerialName(SecurityApiFields.RETRY_AFTER_SECONDS)
    val retryAfterSeconds: Int,

    @SerialName(SecurityApiFields.NUMBER_OF_SYMBOLS)
    val numberOfSymbols: Int,

    @SerialName(SecurityApiFields.EXPIRATION_SECONDS)
    val expirationSeconds: Int
)