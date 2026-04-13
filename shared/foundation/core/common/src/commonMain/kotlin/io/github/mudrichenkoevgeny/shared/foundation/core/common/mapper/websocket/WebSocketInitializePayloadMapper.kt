package io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload

/**
 * Merges this payload into [existingClientInfo] [ClientInfo] (device fields and [ClientInfo.apiVersion] only).
 */
fun WebSocketInitializePayload.mergeClientInfo(
    existingClientInfo: ClientInfo? = null
): ClientInfo {
    val currentClientInfo = existingClientInfo ?: ClientInfo()
    return currentClientInfo.copy(
        deviceInfo = mergeClientDeviceInfo(currentClientInfo.deviceInfo),
        apiVersion = apiVersion ?: currentClientInfo.apiVersion
    )
}

/**
 * Merges this payload into [existingClientDeviceInfo] [ClientDeviceInfo].
 *
 * Non-null fields from the payload override; [WebSocketInitializePayload.clientType] is applied only when
 * [ClientType.fromValueOrNull] returns non-null, otherwise [existingClientDeviceInfo]'s type is kept.
 */
fun WebSocketInitializePayload.mergeClientDeviceInfo(
    existingClientDeviceInfo: ClientDeviceInfo? = null
): ClientDeviceInfo {
    val currentClientDeviceInfo = existingClientDeviceInfo ?: ClientDeviceInfo()
    return currentClientDeviceInfo.copy(
        clientType = clientType?.let { ClientType.fromValueOrNull(it) } ?: currentClientDeviceInfo.clientType,
        language = language ?: currentClientDeviceInfo.language,
        deviceId = deviceId?.toClientDeviceIdOrNull() ?: currentClientDeviceInfo.deviceId,
        deviceName = deviceName ?: currentClientDeviceInfo.deviceName,
        appVersion = appVersion ?: currentClientDeviceInfo.appVersion,
        operationSystemVersion = operationSystemVersion ?: currentClientDeviceInfo.operationSystemVersion
    )
}

fun ClientDeviceInfo.toWebSocketInitializePayload(apiVersion: String?) = WebSocketInitializePayload(
    clientType = clientType?.serialName,
    language = language,
    deviceId = deviceId?.asHexDashString(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion,
    apiVersion = apiVersion
)
