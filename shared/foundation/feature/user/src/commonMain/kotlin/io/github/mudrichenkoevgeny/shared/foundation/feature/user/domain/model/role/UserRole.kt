package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ROLE_USER = "user"
private const val ROLE_STAFF = "staff"
private const val ROLE_ADMIN = "admin"

/**
 * Defines the user's role within the system, used to control access to specific features and administrative tools.
 *
 * @property value String representation of the [UserRole].
 */
@Serializable
enum class UserRole(val value: String) {
    /**
     * Regular user with standard access to application features.
     */
    @SerialName(ROLE_USER)
    USER(ROLE_USER),

    /**
     * Staff member role for operational work (support, moderation, domain-specific workflows)
     * with permissions granted by templates and explicit capabilities.
     */
    @SerialName(ROLE_STAFF)
    STAFF(ROLE_STAFF),

    /**
     * Superuser with full access to system settings, infrastructure, and user management.
     */
    @SerialName(ROLE_ADMIN)
    ADMIN(ROLE_ADMIN);

    companion object {
        /**
         * Returns [UserRole] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserRole? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserRole] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserRole].
         */
        fun fromValueOrThrow(value: String): UserRole =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserRole: '$value'")
    }
}