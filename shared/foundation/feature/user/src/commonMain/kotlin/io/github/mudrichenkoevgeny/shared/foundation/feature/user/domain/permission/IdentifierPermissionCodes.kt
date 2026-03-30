package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for managing and viewing user identifiers (email/phone/external auth IDs).
 *
 * No permission in this object grants read access to admin identifiers.
 */
object IdentifierPermissionCodes {
    /** Delete identifiers of accounts with [UserRole.USER]. */
    val IDENTIFIER_DELETE_FOR_USER = UserPermissionCode("identifier.delete.for.user")

    /** Delete identifiers of accounts with [UserRole.STAFF]. */
    val IDENTIFIER_DELETE_FOR_STAFF = UserPermissionCode("identifier.delete.for.staff")

    /** Read identifier list of [UserRole.USER] accounts with masked sensitive data. */
    val IDENTIFIER_MASKED_GET_LIST_OF_USER = UserPermissionCode("identifier.masked.get.list.of.user")

    /** Read identifier list of [UserRole.STAFF] accounts with masked sensitive data. */
    val IDENTIFIER_MASKED_GET_LIST_OF_STAFF = UserPermissionCode("identifier.masked.get.list.of.staff")

    /** Read identifier list of [UserRole.USER] accounts with unmasked sensitive data. */
    val IDENTIFIER_UNMASKED_GET_LIST_OF_USER = UserPermissionCode("identifier.unmasked.get.list.of.user")

    /** Read identifier list of [UserRole.STAFF] accounts with unmasked sensitive data. */
    val IDENTIFIER_UNMASKED_GET_LIST_OF_STAFF = UserPermissionCode("identifier.unmasked.get.list.of.staff")
}