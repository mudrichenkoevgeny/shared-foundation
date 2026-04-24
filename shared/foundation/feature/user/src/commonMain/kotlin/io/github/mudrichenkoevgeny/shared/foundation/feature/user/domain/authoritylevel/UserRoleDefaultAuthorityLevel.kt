package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.authoritylevel

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Default authority levels assigned by [UserRole].
 * * Authority levels define the hierarchical weight of a user, used to prevent
 * lower-privileged users from modifying higher-privileged ones.
 */
object UserRoleDefaultAuthorityLevel {
    /**
     * Baseline authority level for [UserRole.USER].
     */
    const val USER: Int = 0

    /**
     * Baseline authority level for [UserRole.STAFF].
     * * Set to 50 to allow for granular sub-roles within the staff hierarchy
     * (e.g., Junior Staff at 10, Senior Staff at 80).
     */
    const val STAFF: Int = 50

    /**
     * Baseline authority level for [UserRole.ADMIN].
     * * Represents the highest level of authority within the system.
     */
    const val ADMIN: Int = 100

    /**
     * Returns baseline authority level for the provided [role].
     */
    fun forRole(role: UserRole): Int =
        when (role) {
            UserRole.USER -> USER
            UserRole.STAFF -> STAFF
            UserRole.ADMIN -> ADMIN
        }
}