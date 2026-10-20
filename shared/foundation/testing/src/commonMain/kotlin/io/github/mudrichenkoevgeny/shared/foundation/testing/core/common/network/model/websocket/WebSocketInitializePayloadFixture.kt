package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload

/**
 * Creates a test [WebSocketInitializePayload] initialization payload instance.
 *
 * @param clientType Type of client platform.
 * @param language Preferred language code.
 * @param deviceId Client device ID.
 * @param deviceName Name of the client device.
 * @param appVersion Version of the client application.
 * @param operationSystemVersion Version of the operating system.
 * @param apiVersion Version of the API.
 */
fun createTestWebSocketInitializePayload(
    clientType: String? = ClientType.ANDROID.value,
    language: String? = "en",
    deviceId: String? = "device_12345",
    deviceName: String? = "Test Device",
    appVersion: String? = "1.0.0",
    operationSystemVersion: String? = "Android 14",
    apiVersion: String? = "1.0"
) = WebSocketInitializePayload(
    clientType = clientType,
    language = language,
    deviceId = deviceId,
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion,
    apiVersion = apiVersion
)
