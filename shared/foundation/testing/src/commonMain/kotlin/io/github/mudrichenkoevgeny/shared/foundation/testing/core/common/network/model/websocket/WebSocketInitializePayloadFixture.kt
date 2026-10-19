package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload

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
