package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup

/**
 * Domain model for Time-based One-Time Password (TOTP) setup data.
 *
 * Provides the required secrets and identifiers to provision an external
 * authenticator application for a specific user account.
 *
 * @property secretKey The Base32 encoded secret key for manual entry.
 * @property otpAuthUrl The standard `otpauth://` URI for QR code generation.
 * @property mfaToken A temporary token linking this setup session to a future verification.
 */
data class TotpSetup(
    val secretKey: String,
    val otpAuthUrl: String,
    val mfaToken: String
)