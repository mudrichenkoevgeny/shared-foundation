package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_USER_AGENT

fun createTestClientInfo(
    clientDeviceInfo: ClientDeviceInfo = createTestClientDeviceInfo(),
    userAgent: String? = TEST_USER_AGENT,
    ipAddress: String? = TEST_IP_ADDRESS,
    host: String? = "api.example.com",
    origin: String? = "https://example.com",
    apiVersion: String? = "1.0"
) = ClientInfo(
    clientDeviceInfo = clientDeviceInfo,
    userAgent = userAgent,
    ipAddress = ipAddress,
    host = host,
    origin = origin,
    apiVersion = apiVersion
)
