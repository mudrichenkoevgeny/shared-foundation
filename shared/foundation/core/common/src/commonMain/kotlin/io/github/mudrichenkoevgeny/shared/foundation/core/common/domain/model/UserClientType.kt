package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the type of client application used to access the system.
 */
@Serializable
enum class UserClientType {
    /**
     * Web browser based client.
     */
    @SerialName(CLIENT_WEB)
    WEB,

    /**
     * Android mobile application.
     */
    @SerialName(CLIENT_ANDROID)
    ANDROID,

    /**
     * iOS mobile application.
     */
    @SerialName(CLIENT_IOS)
    IOS,

    /**
     * Desktop application.
     */
    @SerialName(CLIENT_DESKTOP)
    DESKTOP;

    /**
     * String representation of the [UserClientType].
     */
    val serialName: String
        get() = when (this) {
            WEB -> CLIENT_WEB
            ANDROID -> CLIENT_ANDROID
            IOS -> CLIENT_IOS
            DESKTOP -> CLIENT_DESKTOP
        }

    companion object {
        /** Wire value for [WEB] in JSON and headers. */
        const val CLIENT_WEB = "web"

        /** Wire value for [ANDROID] in JSON and headers. */
        const val CLIENT_ANDROID = "android"

        /** Wire value for [IOS] in JSON and headers. */
        const val CLIENT_IOS = "ios"

        /** Wire value for [DESKTOP] in JSON and headers. */
        const val CLIENT_DESKTOP = "desktop"

        /**
         * Returns [UserClientType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name (e.g. `WEB`) or a wire string such as [CLIENT_WEB].
         */
        fun fromValue(value: String): UserClientType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()
    }
}