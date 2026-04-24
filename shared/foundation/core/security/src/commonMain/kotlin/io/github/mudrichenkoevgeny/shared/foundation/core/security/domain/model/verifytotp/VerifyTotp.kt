package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp

/**
 * Domain model for verifying a Time-based One-Time Password (TOTP).
 *
 * Encapsulates the necessary data to validate a security challenge, whether during
 * initial authentication or for elevated access to sensitive operations.
 *
 * @property mfaToken The temporary unique identifier for the authentication challenge.
 * @property code The verification code provided by the user.
 */
data class VerifyTotp(
    val mfaToken: String,
    val code: String
)