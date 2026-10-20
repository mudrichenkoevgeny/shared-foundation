package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.toClientDeviceIdOrThrow

/** Default deterministic client device ID string for unit tests. */
const val TEST_CLIENT_DEVICE_ID = "00000000-0000-4000-8000-000000000030"

/**
 * Creates a test [ClientDeviceId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_CLIENT_DEVICE_ID]).
 */
fun createTestClientDeviceId(
    value: String = TEST_CLIENT_DEVICE_ID
): ClientDeviceId = value.toClientDeviceIdOrThrow()
