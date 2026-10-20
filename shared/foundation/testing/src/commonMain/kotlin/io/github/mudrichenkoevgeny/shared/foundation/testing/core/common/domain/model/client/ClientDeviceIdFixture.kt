package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrThrow

/**
 * Default client device ID string for unit testing.
 */
const val TEST_CLIENT_DEVICE_ID = "00000000-0000-0000-0000-000000000030"

/**
 * Creates a test [ClientDeviceId] domain instance.
 *
 * @param value Raw device ID string.
 */
fun createTestClientDeviceId(
    value: String = TEST_CLIENT_DEVICE_ID
): ClientDeviceId = value.toClientDeviceIdOrThrow()
