package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Baseline permission codes for user and staff management.
 *
 * Keep values stable once published; add new codes instead of renaming existing ones.
 */
object UserPermissionCodes {
    /** Create users with [UserRole.USER]. */
    val USER_CREATE_AS_USER = UserPermissionCode("user.create.as.user")

    /** Create users with [UserRole.STAFF]. */
    val USER_CREATE_AS_STAFF = UserPermissionCode("user.create.as.staff")

    /** Edit users [UserAccountStatus] with [UserRole.USER]. */
    val USER_EDIT_STATUS_FOR_USER = UserPermissionCode("user.edit.status.for.user")

    /** Edit users [UserAccountStatus] with [UserRole.STAFF]. */
    val USER_EDIT_STATUS_FOR_STAFF = UserPermissionCode("user.edit.status.for.staff")

    /** Delete users with [UserRole.USER]. */
    val USER_DELETE_USER = UserPermissionCode("user.delete.user")

    /** Delete users with [UserRole.STAFF]. */
    val USER_DELETE_STAFF = UserPermissionCode("user.delete.staff")

    /** Get list of users with [UserRole.USER]. */
    val USER_GET_LIST_OF_USER = UserPermissionCode("user.get.list.of.user")

    /** Get list of users with [UserRole.STAFF]. */
    val USER_GET_LIST_OF_STAFF = UserPermissionCode("user.get.list.of.staff")
}