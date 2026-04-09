package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.audit.api.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Default permission templates assigned by [UserRole].
 *
 * These sets provide baseline access only. Consumers may extend or override them
 * in role/policy management flows.
 */
object UserRoleDefaultPermissionCode {
    /**
     * Baseline permissions for [UserRole.USER].
     */
    val USER: Set<PermissionCode> = emptySet()

    /**
     * Baseline permissions for [UserRole.STAFF].
     */
    val STAFF: Set<PermissionCode> = setOf(
        UserPermissionCode.USER_CREATE_AS_USER,
        UserPermissionCode.USER_UPDATE_STATUS_FOR_USER,
        UserPermissionCode.USER_DELETE_FOR_USER,
        UserPermissionCode.USER_GET_OF_USER,
        IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_USER,
        IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED,
        SessionPermissionCode.SESSION_DELETE_FOR_USER,
        SessionPermissionCode.SESSION_GET_OF_USER_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED
    )

    /**
     * Baseline permissions for [UserRole.ADMIN].
     *
     * Includes every [PermissionCode] exposed by: [UserPermissionCode], [IdentifierPermissionCode],
     * [SessionPermissionCode], [AuditPermissionCode], [SecurityPermissionCode], and [SettingsPermissionCode].
     */
    val ADMIN: Set<PermissionCode> = setOf(
        UserPermissionCode.USER_CREATE_AS_USER,
        UserPermissionCode.USER_CREATE_AS_STAFF,
        UserPermissionCode.USER_UPDATE_STATUS_FOR_USER,
        UserPermissionCode.USER_UPDATE_STATUS_FOR_STAFF,
        UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_USER,
        UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_STAFF,
        UserPermissionCode.USER_DELETE_FOR_USER,
        UserPermissionCode.USER_DELETE_FOR_STAFF,
        UserPermissionCode.USER_GET_OF_USER,
        UserPermissionCode.USER_GET_OF_STAFF,
        IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_USER,
        IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_STAFF,
        IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED,
        IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED,
        IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED,
        IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED,
        SessionPermissionCode.SESSION_DELETE_FOR_USER,
        SessionPermissionCode.SESSION_DELETE_FOR_STAFF,
        SessionPermissionCode.SESSION_GET_OF_USER_MASKED,
        SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED,
        SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED,
        SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED,
        AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_UNMASKED,
        AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED,
        AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED,
        AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED,
        AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_MASKED,
        AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED,
        SecurityPermissionCode.SECURITY_SETTINGS_UPDATE,
        SettingsPermissionCode.GLOBAL_SETTINGS_UPDATE
    )

    /**
     * Returns baseline permissions for the provided [role].
     */
    fun forRole(role: UserRole): Set<PermissionCode> =
        when (role) {
            UserRole.USER -> USER
            UserRole.STAFF -> STAFF
            UserRole.ADMIN -> ADMIN
        }
}
