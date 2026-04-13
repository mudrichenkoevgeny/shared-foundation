package io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload

fun ClientDeviceInfoPayload.toClientDeviceInfo(): ClientDeviceInfo = ClientDeviceInfo(
    clientType = clientType?.let(ClientType::fromValueOrNull),
    language = language,
    deviceId = deviceId?.toClientDeviceIdOrNull(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)

fun ClientDeviceInfo.toClientDeviceInfoPayload(): ClientDeviceInfoPayload = ClientDeviceInfoPayload(
    clientType = clientType?.serialName,
    language = language,
    deviceId = deviceId?.asHexDashString(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)

fun ClientInfoPayload.toClientInfo(): ClientInfo = ClientInfo(
    deviceInfo = clientDeviceInfo.toClientDeviceInfo(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    apiVersion = apiVersion
)

fun ClientInfo.toClientInfoPayload(): ClientInfoPayload = ClientInfoPayload(
    clientDeviceInfo = deviceInfo.toClientDeviceInfoPayload(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    apiVersion = apiVersion
)
