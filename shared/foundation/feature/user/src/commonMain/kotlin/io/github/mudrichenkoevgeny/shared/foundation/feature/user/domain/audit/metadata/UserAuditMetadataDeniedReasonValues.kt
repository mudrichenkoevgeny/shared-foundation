package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in user-related audit flows (account state or existence).
 */
public object UserAuditMetadataDeniedReasonValues {
    /** * Operation rejected because the user account is read-only.
     * Associated with [UserErrorCodes.USER_READ_ONLY].
     */
    public const val USER_READ_ONLY: String = "user_read_only"

    /** * Operation rejected because the user account is banned.
     * Associated with [UserErrorCodes.USER_BANNED].
     */
    public const val USER_BANNED: String = "user_banned"

    /** * Operation rejected because the user account is locked out.
     * Associated with [UserErrorCodes.USER_LOCKED].
     */
    public const val USER_LOCKED: String = "user_locked"

    /** * Operation rejected because the user account is under a security hold.
     * Associated with [UserErrorCodes.USER_SECURITY_HOLD].
     */
    public const val USER_SECURITY_HOLD: String = "user_security_hold"

    /** * Operation rejected because the user account is pending deletion.
     * Associated with [UserErrorCodes.USER_PENDING_DELETION].
     */
    public const val USER_PENDING_DELETION: String = "user_pending_deletion"

    /** * Operation rejected because no user exists for the referenced id.
     * Associated with [UserErrorCodes.USER_NOT_FOUND].
     */
    public const val USER_NOT_FOUND: String = "user_not_found"

    /** * Operation rejected because the user role is not allowed to perform this action.
     * Associated with [UserErrorCodes.USER_ROLE_NOT_ALLOWED].
     */
    public const val USER_ROLE_NOT_ALLOWED: String = "user_role_not_allowed"

    /** * Operation rejected because the user is missing specific required permissions.
     * Associated with [UserErrorCodes.USER_MISSING_PERMISSIONS].
     */
    public const val USER_MISSING_PERMISSIONS: String = "user_missing_permissions"

    /** * Operation rejected because the user's authority level is lower than required.
     * Associated with [UserErrorCodes.USER_INSUFFICIENT_AUTHORITY_LEVEL].
     */
    public const val USER_INSUFFICIENT_AUTHORITY_LEVEL: String = "user_insufficient_authority_level"

    /** * Operation rejected because the current account status is incompatible with the requested action.
     * Associated with [UserErrorCodes.USER_ILLEGAL_ACCOUNT_STATUS].
     */
    public const val USER_ILLEGAL_ACCOUNT_STATUS: String = "user_illegal_account_status"

    /** * Operation rejected because access is forbidden for the user.
     * Associated with [UserErrorCodes.USER_FORBIDDEN].
     */
    public const val USER_FORBIDDEN: String = "user_forbidden"

    /** * Operation rejected because the limit for a specific identifier type has been reached.
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED].
     */
    public const val USER_IDENTIFIER_LIMIT_REACHED: String = "user_identifier_limit_reached"

    /** * Operation rejected because the maximum total number of identifiers per account has been reached.
     * Associated with [UserErrorCodes.TOTAL_USER_IDENTIFIERS_LIMIT_REACHED].
     */
    public const val TOTAL_USER_IDENTIFIERS_LIMIT_REACHED: String = "total_user_identifier_limit_reached"

    /** * Operation rejected because initial password setup is required for the account.
     * Associated with [UserErrorCodes.PASSWORD_SETUP_REQUIRED].
     */
    public const val PASSWORD_SETUP_REQUIRED: String = "password_setup_required"

    /** * Operation rejected because the identifier type does not support password credentials.
     * Associated with [UserErrorCodes.USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED].
     */
    public const val USER_IDENTIFIER_PASSWORD_NOT_SUPPORTED: String = "user_identifier_password_not_supported"

    /** * Operation rejected because no password credential is set on the identifier record.
     * Associated with [UserErrorCodes.USER_IDENTIFIER_PASSWORD_NOT_SET].
     */
    public const val USER_IDENTIFIER_PASSWORD_NOT_SET: String = "user_identifier_password_not_set"
}
