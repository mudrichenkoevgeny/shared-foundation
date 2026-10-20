package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_USER_AGENT

/**
 * Creates a test [ClientInfoPayload] wire object instance.
 *
 * @param clientDeviceInfo Client device information payload.
 * @param userAgent User agent string.
 * @param ipAddress IP address string.
 * @param apiVersion Version of the API being called.
 */
fun createTestClientInfoPayload(
    clientDeviceInfo: ClientDeviceInfoPayload = createTestClientDeviceInfoPayload(),
    userAgent: String? = TEST_USER_AGENT,
    ipAddress: String? = TEST_IP_ADDRESS,
    apiVersion: String? = "1.0"
) = ClientInfoPayload(
    clientDeviceInfo = clientDeviceInfo,
    userAgent = userAgent,
    ipAddress = ipAddress,
    apiVersion = apiVersion
)
