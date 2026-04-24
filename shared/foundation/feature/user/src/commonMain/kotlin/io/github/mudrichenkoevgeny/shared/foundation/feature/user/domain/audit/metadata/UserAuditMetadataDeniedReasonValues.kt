package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in user-related audit flows (account state or existence).
 */
object UserAuditMetadataDeniedReasonValues {
    /** * Operation rejected because the user account is read-only.
     * Associated with [UserErrorCodes.USER_READ_ONLY].
     */
    const val USER_READ_ONLY = "user_read_only"

    /** * Operation rejected because the user account is blocked.
     * Associated with [UserErrorCodes.USER_BLOCKED].
     */
    const val USER_BLOCKED = "user_blocked"

    /** * Operation rejected because the user account is under a security hold.
     * Associated with [UserErrorCodes.USER_SECURITY_HOLD].
     */
    const val USER_SECURITY_HOLD = "user_security_hold"

    /** * Operation rejected because the user account is pending deletion.
     * Associated with [UserErrorCodes.USER_PENDING_DELETION].
     */
    const val USER_PENDING_DELETION = "user_pending_deletion"

    /** * Operation rejected because no user exists for the referenced id.
     * Associated with [UserErrorCodes.USER_NOT_FOUND].
     */
    const val USER_NOT_FOUND = "user_not_found"

    /** * Operation rejected because the user role is not allowed to perform this action.
     * Associated with [UserErrorCodes.USER_ROLE_NOT_ALLOWED].
     */
    const val USER_ROLE_NOT_ALLOWED = "user_role_not_allowed"

    /** * Operation rejected because the user is missing specific required permissions.
     * Associated with [UserErrorCodes.USER_MISSING_PERMISSIONS].
     */
    const val USER_MISSING_PERMISSIONS = "user_missing_permissions"

    /** * Operation rejected because the user's authority level is lower than required.
     * Associated with [UserErrorCodes.USER_INSUFFICIENT_AUTHORITY_LEVEL].
     */
    const val USER_INSUFFICIENT_AUTHORITY_LEVEL = "user_insufficient_authority_level"

    /** * Operation rejected because the current account status is incompatible with the requested action.
     * Associated with [UserErrorCodes.USER_ILLEGAL_ACCOUNT_STATUS].
     */
    const val USER_ILLEGAL_ACCOUNT_STATUS = "user_illegal_account_status"

    /** * Operation rejected because access is forbidden for the user.
     * Associated with [UserErrorCodes.USER_FORBIDDEN].
     */
    const val USER_FORBIDDEN = "user_forbidden"

    /** * Operation rejected because the limit for a specific identifier type has been reached.
     * Associated with [UserErrorCodes.USER_IDENTIFIER_LIMIT_REACHED].
     */
    const val USER_IDENTIFIER_LIMIT_REACHED = "user_identifier_limit_reached"

    /** * Operation rejected because the maximum total number of identifiers per account has been reached.
     * Associated with [UserErrorCodes.TOTAL_USER_IDENTIFIERS_LIMIT_REACHED].
     */
    const val TOTAL_USER_IDENTIFIERS_LIMIT_REACHED = "total_user_identifier_limit_reached"
}