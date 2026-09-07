package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role

/**
 * Defines the user's role within the system, used to control access to specific features and administrative tools.
 */
enum class UserRole {
    /**
     * Regular user with standard access to application features.
     */
    USER,

    /**
     * Staff member role for operational work (support, moderation, domain-specific workflows)
     * with permissions granted by templates and explicit capabilities.
     */
    STAFF,

    /**
     * Superuser with full access to system settings, infrastructure, and user management.
     */
    ADMIN;

    /**
     * String representation of the [UserRole].
     */
    val serialName: String
        get() = when (this) {
            USER -> ROLE_USER
            STAFF -> ROLE_STAFF
            ADMIN -> ROLE_ADMIN
        }

    companion object {
        private const val ROLE_USER = "user"
        private const val ROLE_STAFF = "staff"
        private const val ROLE_ADMIN = "admin"

        /**
         * Returns [UserRole] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserRole? =
            entries.firstOrNull {
                it.serialName.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            } ?: runCatching {
                valueOf(value.uppercase())
            }.getOrNull()

        /**
         * Returns [UserRole] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserRole].
         */
        fun fromValueOrThrow(value: String): UserRole =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserRole: '$value'")
    }
}