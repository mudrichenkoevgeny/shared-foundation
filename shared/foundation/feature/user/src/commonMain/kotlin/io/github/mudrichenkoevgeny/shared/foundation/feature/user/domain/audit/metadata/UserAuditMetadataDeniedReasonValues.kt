package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in user-related audit flows (account state or existence).
 */
object UserAuditMetadataDeniedReasonValues {
    /** Operation rejected because the user account is read-only. */
    const val USER_READ_ONLY = "user_read_only"

    /** Operation rejected because the user account is blocked. */
    const val USER_BLOCKED = "user_blocked"

    /** Operation rejected because the user account is under a security hold. */
    const val USER_SECURITY_HOLD = "user_security_hold"

    /** Operation rejected because the user account is pending deletion. */
    const val USER_PENDING_DELETION = "user_pending_deletion"

    /** Operation rejected because no user exists for the referenced id. */
    const val USER_NOT_FOUND = "user_not_found"

    /** Operation rejected because the user role is not allowed to perform this action. */
    const val USER_ROLE_NOT_ALLOWED = "user_role_not_allowed"

    /** Operation rejected because the user is missing specific required permissions. */
    const val USER_MISSING_PERMISSIONS = "user_missing_permissions"

    /** Operation rejected because access is forbidden for the user. */
    const val USER_FORBIDDEN = "user_forbidden"
}
