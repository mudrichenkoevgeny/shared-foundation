package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

/**
 * Permission codes for managing and viewing user sessions.
 *
 * Security policy: sessions of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.ADMIN] are intentionally out of scope.
 * No permission in this object grants read access to admin sessions.
 */
object UserSessionPermissionCodes {
    /** Delete/revoke sessions of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER]. */
    val USER_SESSION_DELETE_FOR_USER = UserPermissionCode("user.session.delete.for.user")

    /** Delete/revoke sessions of accounts with [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF]. */
    val USER_SESSION_DELETE_FOR_STAFF = UserPermissionCode("user.session.delete.for.staff")

    /** Read session list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER] accounts with masked sensitive data. */
    val USER_SESSION_MASKED_GET_LIST_OF_USER = UserPermissionCode("user.session.masked.get.list.of.user")

    /** Read session list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF] accounts with masked sensitive data. */
    val USER_SESSION_MASKED_GET_LIST_OF_STAFF = UserPermissionCode("user.session.masked.get.list.of.staff")

    /** Read session list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.USER] accounts with unmasked sensitive data. */
    val USER_SESSION_UNMASKED_GET_LIST_OF_USER = UserPermissionCode("user.session.unmasked.get.list.of.user")

    /** Read session list of [io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole.STAFF] accounts with unmasked sensitive data. */
    val USER_SESSION_UNMASKED_GET_LIST_OF_STAFF = UserPermissionCode("user.session.unmasked.get.list.of.staff")
}