package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for viewing user audit records.
 *
 * Security policy: audit records of accounts with [UserRole.ADMIN] are intentionally out of scope.
 * No permission in this object grants read access to admin audit logs.
 */
object UserAuditPermissionCodes {
    /** Read audit records of [UserRole.USER] accounts with masked sensitive data. */
    val USER_AUDIT_MASKED_READ_FOR_USER = UserPermissionCode("audit.masked.read.for.user")

    /** Read audit records of [UserRole.STAFF] accounts with masked sensitive data. */
    val USER_AUDIT_MASKED_READ_FOR_STAFF = UserPermissionCode("audit.masked.read.for.staff")

    /** Read audit records of [UserRole.USER] accounts with unmasked sensitive data. */
    val USER_AUDIT_UNMASKED_READ_FOR_USER = UserPermissionCode("audit.unmasked.read.for.user")

    /** Read audit records of [UserRole.STAFF] accounts with unmasked sensitive data. */
    val USER_AUDIT_UNMASKED_READ_FOR_STAFF = UserPermissionCode("audit.unmasked.read.for.staff")
}