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
    IOS;

    /**
     * String representation of the [UserClientType].
     */
    val serialName: String
        get() = when (this) {
            WEB -> CLIENT_WEB
            ANDROID -> CLIENT_ANDROID
            IOS -> CLIENT_IOS
        }

    companion object {
        const val CLIENT_WEB = "web"
        const val CLIENT_ANDROID = "android"
        const val CLIENT_IOS = "ios"

        /**
         * Returns [UserClientType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValue(value: String): UserClientType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()
    }
}