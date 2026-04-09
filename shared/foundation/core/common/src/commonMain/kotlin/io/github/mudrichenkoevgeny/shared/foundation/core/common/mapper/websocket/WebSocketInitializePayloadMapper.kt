package io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload

/**
 * Merges this payload into [existingClientDeviceInfo] [ClientDeviceInfo].
 *
 * Non-null fields from the payload override; [WebSocketInitializePayload.clientType] is applied only when
 * [ClientType.fromValueOrNull] returns non-null, otherwise [existingClientDeviceInfo]'s type is kept.
 */
fun WebSocketInitializePayload.mergeClientDeviceInfo(
    existingClientDeviceInfo: ClientDeviceInfo
): ClientDeviceInfo = ClientDeviceInfo(
    clientType = clientType?.let { ClientType.fromValueOrNull(it) } ?: existingClientDeviceInfo.clientType,
    language = language ?: existingClientDeviceInfo.language,
    deviceId = deviceId?.toClientDeviceIdOrNull(),
    deviceName = deviceName ?: existingClientDeviceInfo.deviceName,
    appVersion = appVersion ?: existingClientDeviceInfo.appVersion,
    operationSystemVersion = operationSystemVersion ?: existingClientDeviceInfo.operationSystemVersion
)

/**
 * Merges this payload into [existingClientInfo] [ClientInfo] (device fields and [ClientInfo.apiVersion] only).
 */
fun WebSocketInitializePayload.mergeClientInfo(
    existingClientInfo: ClientInfo
): ClientInfo = ClientInfo(
    deviceInfo = mergeClientDeviceInfo(existingClientInfo.deviceInfo),
    userAgent = existingClientInfo.userAgent,
    ipAddress = existingClientInfo.ipAddress,
    host = existingClientInfo.host,
    origin = existingClientInfo.origin,
    apiVersion = apiVersion ?: existingClientInfo.apiVersion
)

fun ClientDeviceInfo.toWebSocketInitializePayload(apiVersion: String?) = WebSocketInitializePayload(
    clientType = clientType?.serialName,
    language = language,
    deviceId = deviceId?.asHexDashString(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion,
    apiVersion = apiVersion
)
