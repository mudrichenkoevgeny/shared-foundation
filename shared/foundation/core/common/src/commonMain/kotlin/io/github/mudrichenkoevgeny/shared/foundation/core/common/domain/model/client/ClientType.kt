package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

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

    fun isMobileClient(): Boolean = this == ClientType.ANDROID || this == ClientType.IOS

    companion object {
        private const val CLIENT_WEB = "web"
        private const val CLIENT_ANDROID = "android"
        private const val CLIENT_IOS = "ios"
        private const val CLIENT_DESKTOP = "desktop"

        /**
         * Returns [ClientType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name (e.g. `WEB`) or a wire string such as [CLIENT_WEB].
         */
        fun fromValueOrNull(value: String): ClientType? =
            entries.firstOrNull {
                it.serialName.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            } ?: runCatching {
                valueOf(value.uppercase())
            }.getOrNull()

        /**
         * Returns [ClientType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [ClientType].
         */
        fun fromValueOrThrow(value: String): ClientType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for ClientType: '$value'")
    }
}