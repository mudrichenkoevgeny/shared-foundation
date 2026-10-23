package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy

/**
 * Security-specific error codes for API responses and backend logging, used to categorize failures.
 */
object SecurityErrorCodes {
    /**
     * Top-level error code when a password fails policy validation ([ManagementPasswordPolicy]).
     *
     * **Localization keys:**
     * - `password_too_weak`: General top-level localized error message ("Password is too weak").
     * - Granular validation hints (read from `error.args` flags on client UI forms):
     *   - `password_too_short_with_args` (`{minLength}`): When `args.passwordTooShort == true`.
     *   - `password_no_letter`: When `args.passwordNoLetter == true`.
     *   - `password_no_uppercase`: When `args.passwordNoUpperCase == true`.
     *   - `password_no_lowercase`: When `args.passwordNoLowerCase == true`.
     *   - `password_no_digit`: When `args.passwordNoDigit == true`.
     *   - `password_no_special_char`: When `args.passwordNoSpecialChar == true`.
     *   - `password_too_common`: When `args.passwordTooCommon == true`.
     *
     * **Public arguments (Programmatic API payload):**
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
     * **Localization keys:**
     * - `otp_retry_too_soon_without_args`: Static fallback message.
     * - `otp_retry_too_soon_with_args`: Parameterized message template (`{retryAfterSeconds}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Remaining wait time in seconds (`{retryAfterSeconds}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Remaining wait time in seconds.
     */
    const val OTP_RETRY_TOO_SOON = "OTP_RETRY_TOO_SOON"

    /**
     * TOTP is already configured and active for this account.
     *
     * **Localization keys:**
     * - `totp_already_enabled`
     */
    const val TOTP_ALREADY_ENABLED = "TOTP_ALREADY_ENABLED"

    /**
     * Operation failed because TOTP is not enabled for this account.
     *
     * **Localization keys:**
     * - `totp_not_enabled`
     */
    const val TOTP_NOT_ENABLED = "TOTP_NOT_ENABLED"

    /**
     * Action requires confirmation via Multi-Factor Authentication (MFA).
     *
     * **Localization keys:**
     * - `mfa_confirmation_required`
     *
     * **Public arguments (Programmatic API payload):**
     * - [SecurityErrorArgs.MFA_TOKEN]: Technical challenge token required for the subsequent verification request.
     */
    const val MFA_CONFIRMATION_REQUIRED = "MFA_CONFIRMATION_REQUIRED"

    /**
     * The provided MFA challenge token has expired.
     *
     * **Localization keys:**
     * - `mfa_token_expired`
     */
    const val MFA_TOKEN_EXPIRED = "MFA_TOKEN_EXPIRED"

    /**
     * The recovery code has already been used and is no longer valid.
     *
     * **Localization keys:**
     * - `recovery_code_already_used`
     */
    const val RECOVERY_CODE_ALREADY_USED = "RECOVERY_CODE_ALREADY_USED"

    /**
     * The TOTP or recovery code provided is incorrect.
     *
     * **Localization keys:**
     * - `invalid_totp_code`
     */
    const val INVALID_TOTP_CODE = "INVALID_TOTP_CODE"

    /**
     * The provided MFA challenge token is invalid, malformed, or belongs to another session.
     *
     * **Localization keys:**
     * - `mfa_token_expired`
     */
    const val INVALID_MFA_TOKEN = "INVALID_MFA_TOKEN"

    /**
     * Request was rejected because the client IP address is not permitted by IP restriction policy.
     *
     * **Localization keys:**
     * - `ip_not_allowed`
     */
    const val IP_NOT_ALLOWED = "IP_NOT_ALLOWED"
}
