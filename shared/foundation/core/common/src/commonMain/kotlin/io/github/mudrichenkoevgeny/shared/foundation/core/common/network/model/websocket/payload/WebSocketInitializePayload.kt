package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.payload

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.UserClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for the initial WebSocket handshake message to provide client-specific metadata.
 *
 * @property clientType The type of client application associated with this session (e.g. [UserClientType]).
 * @property language The preferred language of the user, which may override the initial HTTP header value.
 * @property deviceId A unique identifier for the specific device or browser instance.
 * @property deviceName A human-readable name for the device (e.g., "Chrome on Windows", "iPhone 15").
 * @property appVersion The current version of the client application.
 * @property operationSystemVersion The version of the operating system running on the client device (e.g., "iOS 17.4", "Android 14", "Windows 11").
 */
@Serializable
data class WebSocketInitializePayload(
    @SerialName(CommonApiFields.CLIENT_TYPE)
    val clientType: String? = null,

    @SerialName(CommonApiFields.LANGUAGE)
    val language: String? = null,

    @SerialName(CommonApiFields.DEVICE_ID)
    val deviceId: String,

    @SerialName(CommonApiFields.DEVICE_NAME)
    val deviceName: String? = null,

    @SerialName(CommonApiFields.APP_VERSION)
    val appVersion: String? = null,

    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    val operationSystemVersion: String? = null
)