package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val CLIENT_WEB = "web"
private const val CLIENT_ANDROID = "android"
private const val CLIENT_IOS = "ios"
private const val CLIENT_DESKTOP = "desktop"

/**
 * Represents the type of client application used to access the system.
 *
 * @property value String representation of the [ClientType].
 */
@Serializable
enum class ClientType(val value: String) {
    /**
     * Web browser based client.
     */
    @SerialName(CLIENT_WEB)
    WEB(CLIENT_WEB),

    /**
     * Android mobile application.
     */
    @SerialName(CLIENT_ANDROID)
    ANDROID(CLIENT_ANDROID),

    /**
     * iOS mobile application.
     */
    @SerialName(CLIENT_IOS)
    IOS(CLIENT_IOS),

    /**
     * Desktop application.
     */
    @SerialName(CLIENT_DESKTOP)
    DESKTOP(CLIENT_DESKTOP);

    fun isMobileClient(): Boolean = this == ANDROID || this == IOS

    companion object {
        /**
         * Returns [ClientType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name (e.g. `WEB`) or a wire string such as [CLIENT_WEB].
         */
        fun fromValueOrNull(value: String): ClientType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [ClientType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [ClientType].
         */
        fun fromValueOrThrow(value: String): ClientType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for ClientType: '$value'")
    }
}