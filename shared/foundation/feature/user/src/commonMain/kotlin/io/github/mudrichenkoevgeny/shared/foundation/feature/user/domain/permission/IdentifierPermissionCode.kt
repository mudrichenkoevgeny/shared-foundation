package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Permission codes for managing and viewing user identifiers (email/phone/external auth IDs).
 *
 * No permission in this object grants read access to admin identifiers.
 */
public object IdentifierPermissionCode {
    /** Delete identifiers of accounts with [UserRole.USER]. */
    public val IDENTIFIER_DELETE_FOR_USER: PermissionCode = PermissionCode("identifier.delete.for.user")

    /** Delete identifiers of accounts with [UserRole.STAFF]. */
    public val IDENTIFIER_DELETE_FOR_STAFF: PermissionCode = PermissionCode("identifier.delete.for.staff")

    /** Delete or revoke passwords on identifiers of accounts with [UserRole.USER]. */
    public val IDENTIFIER_DELETE_PASSWORD_FOR_USER: PermissionCode = PermissionCode("identifier.delete.password.for.user")

    /** Delete or revoke passwords on identifiers of accounts with [UserRole.STAFF]. */
    public val IDENTIFIER_DELETE_PASSWORD_FOR_STAFF: PermissionCode = PermissionCode("identifier.delete.password.for.staff")

    /** Read identifier list of [UserRole.USER] accounts with masked sensitive data. */
    public val IDENTIFIER_GET_OF_USER_MASKED: PermissionCode = PermissionCode("identifier.get.of.user.masked")

    /** Read identifier list of [UserRole.USER] accounts with unmasked sensitive data. */
    public val IDENTIFIER_GET_OF_USER_UNMASKED: PermissionCode = PermissionCode("identifier.get.of.user.unmasked")

    /** Read identifier list of [UserRole.STAFF] accounts with masked sensitive data. */
    public val IDENTIFIER_GET_OF_STAFF_MASKED: PermissionCode = PermissionCode("identifier.get.of.staff.masked")

    /** Read identifier list of [UserRole.STAFF] accounts with unmasked sensitive data. */
    public val IDENTIFIER_GET_OF_STAFF_UNMASKED: PermissionCode = PermissionCode("identifier.get.of.staff.unmasked")

    /**
     * All identifier permissions.
     */
    public val ALL: Set<PermissionCode> =
        setOf(
            IDENTIFIER_DELETE_FOR_USER,
            IDENTIFIER_DELETE_FOR_STAFF,
            IDENTIFIER_DELETE_PASSWORD_FOR_USER,
            IDENTIFIER_DELETE_PASSWORD_FOR_STAFF,
            IDENTIFIER_GET_OF_USER_MASKED,
            IDENTIFIER_GET_OF_USER_UNMASKED,
            IDENTIFIER_GET_OF_STAFF_MASKED,
            IDENTIFIER_GET_OF_STAFF_UNMASKED
        )
}
