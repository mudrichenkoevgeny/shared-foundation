package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val TYPE_NONE = "none"
private const val TYPE_INDEFINITE = "indefinite"
private const val TYPE_TEMPORARY = "temporary"

/**
 * Represents the type of account lockout or restriction state.
 *
 * @property value String representation of the [AccountLockoutType].
 */
@Serializable
enum class AccountLockoutType(val value: String) {
    /**
     * Account is not locked out.
     */
    @SerialName(TYPE_NONE)
    NONE(TYPE_NONE),

    /**
     * Account is indefinitely locked out.
     */
    @SerialName(TYPE_INDEFINITE)
    INDEFINITE(TYPE_INDEFINITE),

    /**
     * Account is temporarily locked out until a specific timestamp.
     */
    @SerialName(TYPE_TEMPORARY)
    TEMPORARY(TYPE_TEMPORARY);

    companion object {
        /**
         * Returns [AccountLockoutType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AccountLockoutType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [AccountLockoutType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AccountLockoutType].
         */
        fun fromValueOrThrow(value: String): AccountLockoutType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for AccountLockoutType: '$value'")
    }
}