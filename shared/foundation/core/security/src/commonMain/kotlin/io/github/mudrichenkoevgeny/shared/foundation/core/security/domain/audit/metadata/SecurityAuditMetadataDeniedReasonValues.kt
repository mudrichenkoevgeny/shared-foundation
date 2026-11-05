package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in security-related audit flows (MFA, password policy, etc.).
 */
public object SecurityAuditMetadataDeniedReasonValues {
    /**
     * Client exceeded a rate or quota limit.
     * Associated with [SecurityErrorCodes.OTP_RETRY_TOO_SOON].
     */
    public const val RATE_LIMIT: String = "rate_limit"

    /**
     * Password does not satisfy the configured [ManagementPasswordPolicy].
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_TOO_WEAK: String = "password_too_weak"

    /**
     * The MFA challenge token has expired and is no longer valid for processing.
     * Associated with [SecurityErrorCodes.MFA_TOKEN_EXPIRED].
     */
    public const val MFA_TOKEN_EXPIRED: String = "mfa_token_expired"

    /**
     * TOTP setup or activation is denied because it is already active.
     * Associated with [SecurityErrorCodes.TOTP_ALREADY_ENABLED].
     */
    public const val TOTP_ALREADY_ENABLED: String = "totp_already_enabled"

    /**
     * Security operation denied because TOTP is not set up.
     * Associated with [SecurityErrorCodes.TOTP_NOT_ENABLED].
     */
    public const val TOTP_NOT_ENABLED: String = "totp_not_enabled"

    /**
     * Access denied because the specific recovery code was previously consumed.
     * Associated with [SecurityErrorCodes.RECOVERY_CODE_ALREADY_USED].
     */
    public const val RECOVERY_CODE_ALREADY_USED: String = "recovery_code_already_used"

    /**
     * Operation denied because a Multi-Factor Authentication step is mandatory.
     * Associated with [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     */
    public const val MFA_REQUIRED: String = "mfa_required"

    /**
     * Operation denied because the provided TOTP or recovery code is incorrect.
     * Associated with [SecurityErrorCodes.INVALID_TOTP_CODE].
     */
    public const val INVALID_TOTP_CODE: String = "invalid_totp_code"

    /**
     * Operation denied because the provided MFA challenge token is invalid or malformed.
     * Associated with [SecurityErrorCodes.INVALID_MFA_TOKEN].
     */
    public const val INVALID_MFA_TOKEN: String = "invalid_mfa_token"

    /**
     * Request rejected because the client IP address is restricted by IP policy.
     * Associated with [SecurityErrorCodes.IP_NOT_ALLOWED].
     */
    public const val IP_NOT_ALLOWED: String = "ip_not_allowed"
}
