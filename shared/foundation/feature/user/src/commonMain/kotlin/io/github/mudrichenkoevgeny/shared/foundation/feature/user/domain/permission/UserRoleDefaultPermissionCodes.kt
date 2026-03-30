package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Default permission templates assigned by [UserRole].
 *
 * These sets provide baseline access only. Consumers may extend or override them
 * in role/policy management flows.
 */
object UserRoleDefaultPermissionCodes {
    /**
     * Baseline permissions for [UserRole.USER].
     */
    val USER: Set<UserPermissionCode> = emptySet()

    /**
     * Baseline permissions for [UserRole.STAFF].
     */
    val STAFF: Set<UserPermissionCode> = setOf(
        UserPermissionCodes.USER_CREATE_AS_USER,
        UserPermissionCodes.USER_EDIT_STATUS_FOR_USER,
        UserPermissionCodes.USER_DELETE_USER,
        UserPermissionCodes.USER_GET_LIST_OF_USER,
        IdentifierPermissionCodes.IDENTIFIER_DELETE_FOR_USER,
        IdentifierPermissionCodes.IDENTIFIER_MASKED_GET_LIST_OF_USER,
        SessionPermissionCodes.SESSION_DELETE_FOR_USER,
        SessionPermissionCodes.SESSION_MASKED_GET_LIST_OF_USER,
        UserAuditPermissionCodes.USER_AUDIT_MASKED_READ_FOR_USER
    )

    /**
     * Baseline permissions for [UserRole.ADMIN].
     *
     * Includes all currently defined user-domain permission codes.
     */
    val ADMIN: Set<UserPermissionCode> = setOf(
        UserPermissionCodes.USER_CREATE_AS_USER,
        UserPermissionCodes.USER_CREATE_AS_STAFF,
        UserPermissionCodes.USER_EDIT_STATUS_FOR_USER,
        UserPermissionCodes.USER_EDIT_STATUS_FOR_STAFF,
        UserPermissionCodes.USER_DELETE_USER,
        UserPermissionCodes.USER_DELETE_STAFF,
        UserPermissionCodes.USER_GET_LIST_OF_USER,
        UserPermissionCodes.USER_GET_LIST_OF_STAFF,
        IdentifierPermissionCodes.IDENTIFIER_DELETE_FOR_USER,
        IdentifierPermissionCodes.IDENTIFIER_DELETE_FOR_STAFF,
        IdentifierPermissionCodes.IDENTIFIER_MASKED_GET_LIST_OF_USER,
        IdentifierPermissionCodes.IDENTIFIER_MASKED_GET_LIST_OF_STAFF,
        IdentifierPermissionCodes.IDENTIFIER_UNMASKED_GET_LIST_OF_USER,
        IdentifierPermissionCodes.IDENTIFIER_UNMASKED_GET_LIST_OF_STAFF,
        SessionPermissionCodes.SESSION_DELETE_FOR_USER,
        SessionPermissionCodes.SESSION_DELETE_FOR_STAFF,
        SessionPermissionCodes.SESSION_MASKED_GET_LIST_OF_USER,
        SessionPermissionCodes.SESSION_MASKED_GET_LIST_OF_STAFF,
        SessionPermissionCodes.SESSION_UNMASKED_GET_LIST_OF_USER,
        SessionPermissionCodes.SESSION_UNMASKED_GET_LIST_OF_STAFF,
        UserAuditPermissionCodes.USER_AUDIT_MASKED_READ_FOR_USER,
        UserAuditPermissionCodes.USER_AUDIT_MASKED_READ_FOR_STAFF,
        UserAuditPermissionCodes.USER_AUDIT_UNMASKED_READ_FOR_USER,
        UserAuditPermissionCodes.USER_AUDIT_UNMASKED_READ_FOR_STAFF
    )

    /**
     * Returns baseline permissions for the provided [role].
     */
    fun forRole(role: UserRole): Set<UserPermissionCode> =
        when (role) {
            UserRole.USER -> USER
            UserRole.STAFF -> STAFF
            UserRole.ADMIN -> ADMIN
        }
}
