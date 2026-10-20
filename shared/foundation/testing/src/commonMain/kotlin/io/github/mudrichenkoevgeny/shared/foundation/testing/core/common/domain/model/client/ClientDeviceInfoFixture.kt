package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrNull

/**
 * Creates a test [ClientDeviceInfo] domain instance.
 *
 * @param deviceId Client device ID.
 * @param deviceName Name of the client device.
 * @param clientType Type of the client platform.
 * @param language Preferred language code.
 * @param appVersion Version of the client application.
 * @param operationSystemVersion Version of the operating system.
 */
fun createTestClientDeviceInfo(
    deviceId: ClientDeviceId? = "dev_123456".toClientDeviceIdOrNull(),
    deviceName: String? = "Test Device",
    clientType: ClientType? = ClientType.ANDROID,
    language: String? = "en-US",
    appVersion: String? = "1.0.0",
    operationSystemVersion: String? = "Android 14"
) = ClientDeviceInfo(
    deviceId = deviceId,
    deviceName = deviceName,
    clientType = clientType,
    language = language,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)
