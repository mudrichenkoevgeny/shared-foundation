package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for the initial WebSocket handshake message to provide client-specific metadata.
 *
 * @property clientType The type of client application associated with this session (e.g. [ClientType]).
 * @property language The preferred language of the user, which may override the initial HTTP header value.
 * @property deviceId A unique identifier for the specific device or browser instance.
 * @property deviceName A human-readable name for the device (e.g., "Chrome on Windows", "iPhone 15").
 * @property appVersion The current version of the client application.
 * @property operationSystemVersion The version of the operating system running on the client device (e.g., "iOS 17.4", "Android 14", "Windows 11").
 * @property apiVersion Negotiated client API version so the server can align response and WebSocket message formats with this SDK build.
 */
@Serializable
public data class WebSocketInitializePayload(
    @SerialName(CommonApiFields.CLIENT_TYPE)
    public val clientType: String?,
    @SerialName(CommonApiFields.LANGUAGE)
    public val language: String?,
    @SerialName(CommonApiFields.DEVICE_ID)
    public val deviceId: String?,
    @SerialName(CommonApiFields.DEVICE_NAME)
    public val deviceName: String?,
    @SerialName(CommonApiFields.APP_VERSION)
    public val appVersion: String?,
    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    public val operationSystemVersion: String?,
    @SerialName(CommonApiFields.API_VERSION)
    public val apiVersion: String?
)
