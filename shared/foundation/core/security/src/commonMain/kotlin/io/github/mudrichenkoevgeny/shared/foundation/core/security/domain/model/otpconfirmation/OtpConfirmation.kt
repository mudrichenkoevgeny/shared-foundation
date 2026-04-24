package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation

/**
 * Configuration for One-Time Password (OTP) generation and validation.
 *
 * @property retryAfterSeconds The cooling-off period in seconds that must elapse before a new OTP can be requested.
 * @property numberOfSymbols The required length of the generated code (e.g., 6 symbols).
 * @property expirationSeconds The time-to-live (TTL) for the generated code, after which it becomes invalid.
 */
data class OtpConfirmation(
    val retryAfterSeconds: Int,
    val numberOfSymbols: Int,
    val expirationSeconds: Int
)