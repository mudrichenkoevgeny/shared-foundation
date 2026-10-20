package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload

/**
 * Creates a test [ClientDeviceInfoPayload] wire object instance.
 *
 * @param clientType Type of client platform.
 * @param language Preferred language code.
 * @param deviceId Client device ID.
 * @param deviceName Name of the client device.
 * @param appVersion Version of the client application.
 * @param operationSystemVersion Version of the operating system.
 */
fun createTestClientDeviceInfoPayload(
    clientType: String? = ClientType.ANDROID.value,
    language: String? = "en-US",
    deviceId: String? = "dev_123456",
    deviceName: String? = "Test Device",
    appVersion: String? = "1.0.0",
    operationSystemVersion: String? = "Android 14"
) = ClientDeviceInfoPayload(
    clientType = clientType,
    language = language,
    deviceId = deviceId,
    deviceName = deviceName,
    appVersion = appVersion,
    operationSystemVersion = operationSystemVersion
)
