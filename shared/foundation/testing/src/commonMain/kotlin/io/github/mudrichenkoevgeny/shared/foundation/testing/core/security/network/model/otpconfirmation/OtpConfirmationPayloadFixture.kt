package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.otpconfirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload

/**
 * Creates a test [OtpConfirmationPayload] wire object instance.
 *
 * @param retryAfterSeconds Delay in seconds before a new OTP can be requested.
 * @param numberOfSymbols Length of the generated OTP code.
 * @param expirationSeconds Expiration time in seconds for generated OTP codes.
 */
fun createTestOtpConfirmationPayload(
    retryAfterSeconds: Int = 60,
    numberOfSymbols: Int = 6,
    expirationSeconds: Int = 300
) = OtpConfirmationPayload(
    retryAfterSeconds = retryAfterSeconds,
    numberOfSymbols = numberOfSymbols,
    expirationSeconds = expirationSeconds
)
