package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload

/**
 * Maps [OtpConfirmationPayload] DTO to domain model [OtpConfirmation].
 */
public fun OtpConfirmationPayload.toOtpConfirmation(): OtpConfirmation = OtpConfirmation(
    retryAfterSeconds = this.retryAfterSeconds,
    numberOfSymbols = this.numberOfSymbols,
    expirationSeconds = this.expirationSeconds
)

/**
 * Maps domain model [OtpConfirmation] to [OtpConfirmationPayload] DTO.
 */
public fun OtpConfirmation.toOtpConfirmationPayload(): OtpConfirmationPayload = OtpConfirmationPayload(
    retryAfterSeconds = this.retryAfterSeconds,
    numberOfSymbols = this.numberOfSymbols,
    expirationSeconds = this.expirationSeconds
)
