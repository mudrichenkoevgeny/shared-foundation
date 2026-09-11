package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout

/**
 * Policy defining account and identifier lockout rules for authentication brute-force protection.
 *
 * @property maxFailedPasswordAttempts Maximum consecutive wrong password attempts before locking the identifier.
 * @property maxFailedOtpAttempts Maximum consecutive wrong OTP code attempts before locking the identifier or operation.
 * @property maxFailedTotpAttempts Maximum consecutive wrong TOTP (authenticator app) attempts before locking the user account.
 * @property failedAttemptsWindowSeconds Time window during which failed attempts accumulate, in seconds.
 * @property lockoutDurationSeconds Duration of the temporary lockout, in seconds.
 * @property permanentLockoutThreshold Number of consecutive temporary lockouts before shifting account status to security hold (0 = disabled).
 * @property isSelfServiceUnlockEnabled Whether users are allowed to self-unlock via OTP or OAuth re-authentication before the lockout timer expires.
 */
data class AccountLockoutPolicy(
    val maxFailedPasswordAttempts: Int,
    val maxFailedOtpAttempts: Int,
    val maxFailedTotpAttempts: Int,
    val failedAttemptsWindowSeconds: Int,
    val lockoutDurationSeconds: Int,
    val permanentLockoutThreshold: Int,
    val isSelfServiceUnlockEnabled: Boolean
)
