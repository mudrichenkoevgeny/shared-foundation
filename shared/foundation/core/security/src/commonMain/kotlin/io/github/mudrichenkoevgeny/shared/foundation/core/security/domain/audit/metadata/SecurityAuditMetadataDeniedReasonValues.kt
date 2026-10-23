package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in security-related audit flows (MFA, password policy, etc.).
 */
object SecurityAuditMetadataDeniedReasonValues {

    /**
     * Client exceeded a rate or quota limit.
     * Associated with [SecurityErrorCodes.OTP_RETRY_TOO_SOON].
     */
    const val RATE_LIMIT = "rate_limit"

    /**
     * Password does not satisfy the configured [ManagementPasswordPolicy].
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_TOO_WEAK = "password_too_weak"

    /**
     * The MFA challenge token has expired and is no longer valid for processing.
     * Associated with [SecurityErrorCodes.MFA_TOKEN_EXPIRED].
     */
    const val MFA_TOKEN_EXPIRED = "mfa_token_expired"

    /**
     * TOTP setup or activation is denied because it is already active.
     * Associated with [SecurityErrorCodes.TOTP_ALREADY_ENABLED].
     */
    const val TOTP_ALREADY_ENABLED = "totp_already_enabled"

    /**
     * Security operation denied because TOTP is not set up.
     * Associated with [SecurityErrorCodes.TOTP_NOT_ENABLED].
     */
    const val TOTP_NOT_ENABLED = "totp_not_enabled"

    /**
     * Access denied because the specific recovery code was previously consumed.
     * Associated with [SecurityErrorCodes.RECOVERY_CODE_ALREADY_USED].
     */
    const val RECOVERY_CODE_ALREADY_USED = "recovery_code_already_used"

    /**
     * Operation denied because a Multi-Factor Authentication step is mandatory.
     * Associated with [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     */
    const val MFA_REQUIRED = "mfa_required"

    /**
     * Operation denied because the provided TOTP or recovery code is incorrect.
     * Associated with [SecurityErrorCodes.INVALID_TOTP_CODE].
     */
    const val INVALID_TOTP_CODE = "invalid_totp_code"

    /**
     * Operation denied because the provided MFA challenge token is invalid or malformed.
     * Associated with [SecurityErrorCodes.INVALID_MFA_TOKEN].
     */
    const val INVALID_MFA_TOKEN = "invalid_mfa_token"

    /**
     * Request rejected because the client IP address is restricted by IP policy.
     * Associated with [SecurityErrorCodes.IP_NOT_ALLOWED].
     */
    const val IP_NOT_ALLOWED = "ip_not_allowed"
}
