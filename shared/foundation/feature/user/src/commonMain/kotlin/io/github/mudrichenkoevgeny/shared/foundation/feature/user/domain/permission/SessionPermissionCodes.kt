package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for managing and viewing user sessions.
 *
 * No permission in this object grants read access to admin sessions.
 */
object SessionPermissionCodes {
    /** Delete/revoke sessions of accounts with [UserRole.USER]. */
    val SESSION_DELETE_FOR_USER = UserPermissionCode("session.delete.for.user")

    /** Delete/revoke sessions of accounts with [UserRole.STAFF]. */
    val SESSION_DELETE_FOR_STAFF = UserPermissionCode("session.delete.for.staff")

    /** Read session list of [UserRole.USER] accounts with masked sensitive data. */
    val SESSION_MASKED_GET_LIST_OF_USER = UserPermissionCode("session.masked.get.list.of.user")

    /** Read session list of [UserRole.STAFF] accounts with masked sensitive data. */
    val SESSION_MASKED_GET_LIST_OF_STAFF = UserPermissionCode("session.masked.get.list.of.staff")

    /** Read session list of [UserRole.USER] accounts with unmasked sensitive data. */
    val SESSION_UNMASKED_GET_LIST_OF_USER = UserPermissionCode("session.unmasked.get.list.of.user")

    /** Read session list of [UserRole.STAFF] accounts with unmasked sensitive data. */
    val SESSION_UNMASKED_GET_LIST_OF_STAFF = UserPermissionCode("session.unmasked.get.list.of.staff")
}