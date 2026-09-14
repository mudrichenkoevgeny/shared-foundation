package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing account and identifier lockout rules for authentication brute-force protection.
 *
 * @property maxFailedPasswordAttempts Maximum consecutive wrong password attempts before locking the identifier.
 * @property maxFailedOtpAttempts Maximum consecutive wrong OTP code attempts before locking the identifier or operation.
 * @property maxFailedTotpAttempts Maximum consecutive wrong TOTP attempts before locking the user account.
 * @property failedAttemptsWindowSeconds Time window during which failed attempts accumulate, in seconds.
 * @property lockoutDurationSeconds Duration of the temporary lockout, in seconds.
 * @property indefiniteLockoutThreshold Number of consecutive temporary lockouts before shifting account status to security hold.
 * @property isSelfServiceUnlockEnabled Whether users are allowed to self-unlock before the lockout timer expires.
 */
@Serializable
data class AccountLockoutPolicyPayload(
    @SerialName(SecurityApiFields.MAX_FAILED_PASSWORD_ATTEMPTS)
    val maxFailedPasswordAttempts: Int,

    @SerialName(SecurityApiFields.MAX_FAILED_OTP_ATTEMPTS)
    val maxFailedOtpAttempts: Int,

    @SerialName(SecurityApiFields.MAX_FAILED_TOTP_ATTEMPTS)
    val maxFailedTotpAttempts: Int,

    @SerialName(SecurityApiFields.FAILED_ATTEMPTS_WINDOW_SECONDS)
    val failedAttemptsWindowSeconds: Int,

    @SerialName(SecurityApiFields.LOCKOUT_DURATION_SECONDS)
    val lockoutDurationSeconds: Int,

    @SerialName(SecurityApiFields.INDEFINITE_LOCKOUT_THRESHOLD)
    val indefiniteLockoutThreshold: Int,

    @SerialName(SecurityApiFields.IS_SELF_SERVICE_UNLOCK_ENABLED)
    val isSelfServiceUnlockEnabled: Boolean
)
