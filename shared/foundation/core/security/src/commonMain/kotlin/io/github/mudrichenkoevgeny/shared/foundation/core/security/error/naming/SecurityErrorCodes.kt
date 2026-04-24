package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy

/**
 * Security-specific error codes for API responses and backend logging, used to categorize failures.
 */
object SecurityErrorCodes {
    /**
     * Action requires confirmation via a TOTP (Time-based One-Time Password).
     *
     * **Public arguments:**
     * - [SecurityErrorArgs.MFA_TOKEN]: The token required for the subsequent verification request.
     */
    const val TOTP_CONFIRMATION_REQUIRED = "TOTP_CONFIRMATION_REQUIRED"

    /**
     * Password does not satisfy the configured [PasswordPolicy].
     *
     * **Public arguments:**
     * - [SecurityErrorArgs.PASSWORD_MIN_LENGTH] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_TOO_SHORT] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_NO_LETTER] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_NO_UPPERCASE] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_NO_LOWERCASE] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_NO_DIGIT] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_NO_SPECIAL_CHAR] (optional)
     * - [SecurityErrorArgs.PASSWORD_FAIL_TOO_COMMON] (optional)
     */
    const val PASSWORD_TOO_WEAK = "PASSWORD_TOO_WEAK"

    /**
     * A new OTP request was made before the cooling-off period had elapsed.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Remaining wait time in seconds.
     */
    const val OTP_RETRY_TOO_SOON = "OTP_RETRY_TOO_SOON"

    /** TOTP is already configured and active for this account. */
    const val TOTP_ALREADY_ENABLED = "TOTP_ALREADY_ENABLED"

    /** Operation failed because TOTP is not enabled for this account. */
    const val TOTP_NOT_ENABLED = "TOTP_NOT_ENABLED"

    /** The provided MFA challenge token has expired. */
    const val MFA_TOKEN_EXPIRED = "MFA_TOKEN_EXPIRED"

    /** The recovery code has already been used and is no longer valid. */
    const val RECOVERY_CODE_ALREADY_USED = "RECOVERY_CODE_ALREADY_USED"

    /** The TOTP or recovery code provided is incorrect. */
    const val INVALID_TOTP_CODE = "INVALID_TOTP_CODE"

    /** The provided MFA challenge token is invalid, malformed, or belongs to another session. */
    const val INVALID_MFA_TOKEN = "INVALID_MFA_TOKEN"
}