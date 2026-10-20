package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.otpconfirmation

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation

/**
 * Creates a test [OtpConfirmation] domain instance.
 *
 * @param retryAfterSeconds Delay in seconds before a new OTP can be requested.
 * @param numberOfSymbols Length of the generated OTP code.
 * @param expirationSeconds Expiration time in seconds for generated OTP codes.
 */
fun createTestOtpConfirmation(
    retryAfterSeconds: Int = 60,
    numberOfSymbols: Int = 6,
    expirationSeconds: Int = 300
) = OtpConfirmation(
    retryAfterSeconds = retryAfterSeconds,
    numberOfSymbols = numberOfSymbols,
    expirationSeconds = expirationSeconds
)
