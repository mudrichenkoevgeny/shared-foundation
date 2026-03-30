package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload

/**
 * Result of requesting a one-time confirmation (OTP) code: rate-limit hint and expected code length.
 *
 * Aligns with [SendConfirmationPayload] on the wire.
 *
 * @property retryAfterSeconds Minimum wait in whole seconds before the client may request another code.
 * @property numberOfSymbols Length of the code the user must enter (digits or characters per server policy).
 */
data class SendConfirmation(
    val retryAfterSeconds: Int,
    val numberOfSymbols: Int
)
