package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for managing and viewing user identifiers (email/phone/external auth IDs).
 *
 * No permission in this object grants read access to admin identifiers.
 */
object IdentifierPermissionCode {
    /** Delete identifiers of accounts with [UserRole.USER]. */
    val IDENTIFIER_DELETE_FOR_USER = PermissionCode("identifier.delete.for.user")

    /** Delete identifiers of accounts with [UserRole.STAFF]. */
    val IDENTIFIER_DELETE_FOR_STAFF = PermissionCode("identifier.delete.for.staff")

    /** Read identifier list of [UserRole.USER] accounts with masked sensitive data. */
    val IDENTIFIER_GET_OF_USER_MASKED = PermissionCode("identifier.get.of.user.masked")

    /** Read identifier list of [UserRole.USER] accounts with unmasked sensitive data. */
    val IDENTIFIER_GET_OF_USER_UNMASKED = PermissionCode("identifier.get.of.user.unmasked")

    /** Read identifier list of [UserRole.STAFF] accounts with masked sensitive data. */
    val IDENTIFIER_GET_OF_STAFF_MASKED = PermissionCode("identifier.get.of.staff.masked")

    /** Read identifier list of [UserRole.STAFF] accounts with unmasked sensitive data. */
    val IDENTIFIER_GET_OF_STAFF_UNMASKED = PermissionCode("identifier.get.of.staff.unmasked")
}