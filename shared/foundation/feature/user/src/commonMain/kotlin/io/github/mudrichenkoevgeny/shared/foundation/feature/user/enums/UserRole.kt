package io.github.mudrichenkoevgeny.shared.foundation.feature.user.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Defines the user's role within the system, used to control access to specific features and administrative tools.
 */
@Serializable
enum class UserRole {
    /**
     * Regular user with standard access to application features.
     */
    @SerialName(ROLE_USER)
    USER,

    /**
     * Privileged user with permissions to manage content and moderate other users.
     */
    @SerialName(ROLE_MODERATOR)
    MODERATOR,

    /**
     * Superuser with full access to system settings, infrastructure, and user management.
     */
    @SerialName(ROLE_ADMIN)
    ADMIN;

    /**
     * String representation of the [UserRole].
     */
    val serialName: String
        get() = when (this) {
            USER -> ROLE_USER
            MODERATOR -> ROLE_MODERATOR
            ADMIN -> ROLE_ADMIN
        }

    companion object {
        const val ROLE_USER = "user"
        const val ROLE_MODERATOR = "moderator"
        const val ROLE_ADMIN = "admin"

        /**
         * Returns [UserRole] based on the provided string value, or null if the value is invalid.
         */
        fun fromValue(value: String): UserRole? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()
    }
}