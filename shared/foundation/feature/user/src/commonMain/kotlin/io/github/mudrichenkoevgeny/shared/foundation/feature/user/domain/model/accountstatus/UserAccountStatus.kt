package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STATUS_ACTIVE = "active"
private const val STATUS_READ_ONLY = "read_only"
private const val STATUS_BANNED = "banned"
private const val STATUS_SECURITY_HOLD = "security_hold"
private const val STATUS_PENDING_DELETION = "pending_deletion"

/**
 * Represents the current state of a user account, determining available permissions and access levels.
 *
 * @property value String representation of the [UserAccountStatus].
 */
@Serializable
enum class UserAccountStatus(val value: String) {
    /**
     * Account is fully functional with no restrictions.
     */
    @SerialName(STATUS_ACTIVE)
    ACTIVE(STATUS_ACTIVE),

    /**
     * Account is restricted to viewing data only.
     */
    @SerialName(STATUS_READ_ONLY)
    READ_ONLY(STATUS_READ_ONLY),

    /**
     * Account is strictly prohibited from accessing the system.
     */
    @SerialName(STATUS_BANNED)
    BANNED(STATUS_BANNED),

    /**
     * Account is under a security hold (e.g. suspected compromise): access is restricted until resolved.
     */
    @SerialName(STATUS_SECURITY_HOLD)
    SECURITY_HOLD(STATUS_SECURITY_HOLD),

    /**
     * Account deletion was requested; the account may be restored until the grace period expires.
     */
    @SerialName(STATUS_PENDING_DELETION)
    PENDING_DELETION(STATUS_PENDING_DELETION);

    companion object {
        /**
         * Returns [UserAccountStatus] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAccountStatus? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserAccountStatus] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAccountStatus].
         */
        fun fromValueOrThrow(value: String): UserAccountStatus =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserAccountStatus: '$value'")
    }
}