package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.clienttype

/**
 * Represents the type of client application used to access the system.
 */
enum class ClientType {
    /**
     * Web browser based client.
     */
    WEB,

    /**
     * Android mobile application.
     */
    ANDROID,

    /**
     * iOS mobile application.
     */
    IOS,

    /**
     * Desktop application.
     */
    DESKTOP;

    /**
     * String representation of the [ClientType].
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
         * Returns [ClientType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name (e.g. `WEB`) or a wire string such as [CLIENT_WEB].
         */
        fun fromValueOrNull(value: String): ClientType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [ClientType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [ClientType].
         */
        fun fromValueOrThrow(value: String): ClientType = valueOf(value.uppercase())
    }
}