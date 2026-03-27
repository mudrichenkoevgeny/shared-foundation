package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the current state of a user account, determining available permissions and access levels.
 */
@Serializable
enum class UserAccountStatus {
    /**
     * Account is fully functional with no restrictions.
     */
    @SerialName(STATUS_ACTIVE)
    ACTIVE,

    /**
     * Account is restricted to viewing data only.
     */
    @SerialName(STATUS_READ_ONLY)
    READ_ONLY,

    /**
     * Account is strictly prohibited from accessing the system.
     */
    @SerialName(STATUS_BANNED)
    BANNED,

    /**
     * Account is under a security hold (e.g. suspected compromise): access is restricted until resolved.
     */
    @SerialName(STATUS_SECURITY_HOLD)
    SECURITY_HOLD,

    /**
     * Account deletion was requested; the account may be restored until the grace period expires.
     */
    @SerialName(STATUS_PENDING_DELETION)
    PENDING_DELETION;

    /**
     * String representation of the [UserAccountStatus].
     */
    val serialName: String
        get() = when (this) {
            ACTIVE -> STATUS_ACTIVE
            READ_ONLY -> STATUS_READ_ONLY
            BANNED -> STATUS_BANNED
            SECURITY_HOLD -> STATUS_SECURITY_HOLD
            PENDING_DELETION -> STATUS_PENDING_DELETION
        }

    companion object {
        const val STATUS_ACTIVE = "active"
        const val STATUS_READ_ONLY = "read_only"
        const val STATUS_BANNED = "banned"
        const val STATUS_SECURITY_HOLD = "security_hold"
        const val STATUS_PENDING_DELETION = "pending_deletion"

        /**
         * Returns [UserAccountStatus] based on the provided string value, or null if the value is invalid.
         */
        fun fromValue(value: String): UserAccountStatus? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()
    }
}