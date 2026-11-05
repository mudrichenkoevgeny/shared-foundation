package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for managing and viewing user sessions.
 *
 * No permission in this object grants read access to admin sessions.
 */
public object SessionPermissionCode {
    /** Delete sessions of accounts with [UserRole.USER]. */
    public val SESSION_DELETE_FOR_USER: PermissionCode = PermissionCode("session.delete.for.user")

    /** Delete sessions of accounts with [UserRole.STAFF]. */
    public val SESSION_DELETE_FOR_STAFF: PermissionCode = PermissionCode("session.delete.for.staff")

    /** Read session list of [UserRole.USER] accounts with masked sensitive data. */
    public val SESSION_GET_OF_USER_MASKED: PermissionCode = PermissionCode("session.get.of.user.masked")

    /** Read session list of [UserRole.USER] accounts with unmasked sensitive data. */
    public val SESSION_GET_OF_USER_UNMASKED: PermissionCode = PermissionCode("session.get.of.user.unmasked")

    /** Read session list of [UserRole.STAFF] accounts with masked sensitive data. */
    public val SESSION_GET_OF_STAFF_MASKED: PermissionCode = PermissionCode("session.get.of.staff.masked")

    /** Read session list of [UserRole.STAFF] accounts with unmasked sensitive data. */
    public val SESSION_GET_OF_STAFF_UNMASKED: PermissionCode = PermissionCode("session.get.of.staff.unmasked")

    /**
     * All session permissions.
     */
    public val ALL: Set<PermissionCode> =
        setOf(
            SESSION_DELETE_FOR_USER,
            SESSION_DELETE_FOR_STAFF,
            SESSION_GET_OF_USER_MASKED,
            SESSION_GET_OF_USER_UNMASKED,
            SESSION_GET_OF_STAFF_MASKED,
            SESSION_GET_OF_STAFF_UNMASKED
        )
}
