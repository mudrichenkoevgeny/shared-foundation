package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for creating users, updating their account state, updating custom permission grants, deleting accounts,
 * and reading user profile data scoped by target [UserRole].
 *
 * Keep values stable once published; add new codes instead of renaming existing ones.
 */
object UserPermissionCode {
    /** Create users with [UserRole.USER]. */
    val USER_CREATE_AS_USER = PermissionCode("user.create.as.user")

    /** Create users with [UserRole.STAFF]. */
    val USER_CREATE_AS_STAFF = PermissionCode("user.create.as.staff")

    /** Update [UserAccountStatus] for accounts with [UserRole.USER]. */
    val USER_UPDATE_STATUS_FOR_USER = PermissionCode("user.update.status.for.user")

    /** Update [UserAccountStatus] for accounts with [UserRole.STAFF]. */
    val USER_UPDATE_STATUS_FOR_STAFF = PermissionCode("user.update.status.for.staff")

    /** Update effective permission grants for accounts with [UserRole.USER]. */
    val USER_UPDATE_PERMISSIONS_FOR_USER = PermissionCode("user.update.permissions.for.user")

    /** Update effective permission grants for accounts with [UserRole.STAFF]. */
    val USER_UPDATE_PERMISSIONS_FOR_STAFF = PermissionCode("user.update.permissions.for.staff")

    /** Update authority level for accounts with [UserRole.USER]. */
    val USER_UPDATE_AUTHORITY_FOR_USER = PermissionCode("user.update.authority.for.user")

    /** Update authority level for accounts with [UserRole.STAFF]. */
    val USER_UPDATE_AUTHORITY_FOR_STAFF = PermissionCode("user.update.authority.for.staff")

    /** Delete accounts with [UserRole.USER]. */
    val USER_DELETE_FOR_USER = PermissionCode("user.delete.for.user")

    /** Delete accounts with [UserRole.STAFF]. */
    val USER_DELETE_FOR_STAFF = PermissionCode("user.delete.for.staff")

    /** Read user data for accounts with [UserRole.USER]. */
    val USER_GET_OF_USER = PermissionCode("user.get.of.user")

    /** Read user data for accounts with [UserRole.STAFF]. */
    val USER_GET_OF_STAFF = PermissionCode("user.get.of.staff")

    /** Update security settings (e.g., TOTP reset, recovery codes) for accounts with [UserRole.USER]. */
    val USER_UPDATE_SECURITY_FOR_USER = PermissionCode("user.update.security.for.user")

    /** Update security settings (e.g., TOTP reset, recovery codes) for accounts with [UserRole.STAFF]. */
    val USER_UPDATE_SECURITY_FOR_STAFF = PermissionCode("user.update.security.for.staff")
}
