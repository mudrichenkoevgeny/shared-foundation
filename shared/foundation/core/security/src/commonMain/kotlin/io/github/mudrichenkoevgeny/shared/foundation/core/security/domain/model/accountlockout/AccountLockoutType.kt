package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout

/**
 * Represents the type of account lockout or restriction state.
 */
enum class AccountLockoutType {
    /**
     * Account is not locked out.
     */
    NONE,

    /**
     * Account is indefinitely locked out.
     */
    INDEFINITE,

    /**
     * Account is temporarily locked out until a specific timestamp.
     */
    TEMPORARY;

    /**
     * String representation of the [AccountLockoutType].
     */
    val serialName: String
        get() = when (this) {
            NONE -> TYPE_NONE
            INDEFINITE -> TYPE_INDEFINITE
            TEMPORARY -> TYPE_TEMPORARY
        }

    companion object {
        private const val TYPE_NONE = "none"
        private const val TYPE_INDEFINITE = "indefinite"
        private const val TYPE_TEMPORARY = "temporary"

        /**
         * Returns [AccountLockoutType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AccountLockoutType? =
            entries.firstOrNull {
                it.serialName.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            } ?: runCatching {
                valueOf(value.uppercase())
            }.getOrNull()

        /**
         * Returns [AccountLockoutType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AccountLockoutType].
         */
        fun fromValueOrThrow(value: String): AccountLockoutType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for AccountLockoutType: '$value'")
    }
}
