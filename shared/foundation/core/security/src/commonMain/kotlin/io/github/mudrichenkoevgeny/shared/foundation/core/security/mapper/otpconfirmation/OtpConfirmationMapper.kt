package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload

/**
 * Maps [OtpConfirmationPayload] DTO to domain model [OtpConfirmation].
 */
fun OtpConfirmationPayload.toOtpConfirmation() = OtpConfirmation(
    retryAfterSeconds = this.retryAfterSeconds,
    numberOfSymbols = this.numberOfSymbols,
    expirationSeconds = this.expirationSeconds
)

/**
 * Maps domain model [OtpConfirmation] to [OtpConfirmationPayload] DTO.
 */
fun OtpConfirmation.toOtpConfirmationPayload() = OtpConfirmationPayload(
    retryAfterSeconds = this.retryAfterSeconds,
    numberOfSymbols = this.numberOfSymbols,
    expirationSeconds = this.expirationSeconds
)