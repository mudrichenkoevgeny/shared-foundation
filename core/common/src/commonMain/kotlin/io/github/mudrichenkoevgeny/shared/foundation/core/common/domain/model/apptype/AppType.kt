package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.apptype

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val APP_TYPE_CLIENT = "client"
private const val APP_TYPE_MANAGEMENT = "management"

/**
 * Defines the operational context of the application.
 *
 * Used to conditionally enable or disable features, UI components, or navigation paths
 * based on whether the application is intended for end-users or internal management staff.
 *
 * @property value String representation of the [AppType].
 */
@Serializable
public enum class AppType(
    public val value: String
) {
    /**
     * Standard client-facing application (e.g., mobile or web user app).
     * Enables all public features such as self-registration and public authentication flows.
     */
    @SerialName(APP_TYPE_CLIENT)
    CLIENT(APP_TYPE_CLIENT),

    /**
     * Internal management or administrative application.
     * Restricts access to public features like self-registration to enforce administrative policies.
     */
    @SerialName(APP_TYPE_MANAGEMENT)
    MANAGEMENT(APP_TYPE_MANAGEMENT);

    public companion object {
        /**
         * Returns [AppType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name (e.g. `CLIENT`) or a wire string such as [APP_TYPE_CLIENT].
         */
        public fun fromValueOrNull(value: String?): AppType? {
            if (value.isNullOrBlank()) return null
            return entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }
        }

        /**
         * Returns [AppType] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [AppType].
         */
        public fun fromValueOrThrow(value: String): AppType =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${AppType::class.simpleName}: '$value'"
            )
    }
}
