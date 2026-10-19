package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrThrow

const val TEST_CLIENT_DEVICE_ID = "00000000-0000-0000-0000-000000000030"

fun createTestClientDeviceId(
    value: String = TEST_CLIENT_DEVICE_ID
): ClientDeviceId = value.toClientDeviceIdOrThrow()
