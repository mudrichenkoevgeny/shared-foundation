package io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload

/**
 * Extension functions mapping client information between domain and network models.
 */

/**
 * Builds domain [ClientDeviceInfo] from [ClientDeviceInfoPayload] DTO.
 *
 * @return domain model aligned with [ClientDeviceInfo] semantics.
 */
fun ClientDeviceInfoPayload.toClientDeviceInfo(): ClientDeviceInfo = ClientDeviceInfo(
    clientType = clientType?.let(ClientType::fromValueOrNull),
    language = language,
    deviceId = deviceId?.toClientDeviceIdOrNull(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)

/**
 * Builds network [ClientDeviceInfoPayload] DTO from domain [ClientDeviceInfo].
 *
 * @return payload DTO aligned with [ClientDeviceInfoPayload] contract.
 */
fun ClientDeviceInfo.toClientDeviceInfoPayload(): ClientDeviceInfoPayload = ClientDeviceInfoPayload(
    clientType = clientType?.serialName,
    language = language,
    deviceId = deviceId?.asHexDashString(),
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)

/**
 * Builds domain [ClientInfo] from [ClientInfoPayload] DTO.
 *
 * @return domain model aligned with [ClientInfo] semantics.
 */
fun ClientInfoPayload.toClientInfo(): ClientInfo = ClientInfo(
    deviceInfo = clientDeviceInfo.toClientDeviceInfo(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    apiVersion = apiVersion
)

/**
 * Builds network [ClientInfoPayload] DTO from domain [ClientInfo].
 *
 * @return payload DTO aligned with [ClientInfoPayload] contract.
 */
fun ClientInfo.toClientInfoPayload(): ClientInfoPayload = ClientInfoPayload(
    clientDeviceInfo = deviceInfo.toClientDeviceInfoPayload(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    apiVersion = apiVersion
)
