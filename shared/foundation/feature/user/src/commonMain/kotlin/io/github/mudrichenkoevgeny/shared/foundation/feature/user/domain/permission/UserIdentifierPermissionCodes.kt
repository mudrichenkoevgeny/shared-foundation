package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

/**
 * Permission codes for managing and viewing user identifiers (email/phone/external auth IDs).
 *
 * Security policy: identifiers of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.ADMIN] are intentionally out of scope.
 * No permission in this object grants read access to admin identifiers.
 */
object UserIdentifierPermissionCodes {
    /** Delete identifiers of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER]. */
    val USER_IDENTIFIER_DELETE_FOR_USER = UserPermissionCode("user.identifier.delete.for.user")

    /** Delete identifiers of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF]. */
    val USER_IDENTIFIER_DELETE_FOR_STAFF = UserPermissionCode("user.identifier.delete.for.staff")

    /** Read identifier list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER] accounts with masked sensitive data. */
    val USER_IDENTIFIER_MASKED_GET_LIST_OF_USER = UserPermissionCode("user.identifier.masked.get.list.of.user")

    /** Read identifier list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF] accounts with masked sensitive data. */
    val USER_IDENTIFIER_MASKED_GET_LIST_OF_STAFF = UserPermissionCode("user.identifier.masked.get.list.of.staff")

    /** Read identifier list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER] accounts with unmasked sensitive data. */
    val USER_IDENTIFIER_UNMASKED_GET_LIST_OF_USER = UserPermissionCode("user.identifier.unmasked.get.list.of.user")

    /** Read identifier list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF] accounts with unmasked sensitive data. */
    val USER_IDENTIFIER_UNMASKED_GET_LIST_OF_STAFF = UserPermissionCode("user.identifier.unmasked.get.list.of.staff")
}