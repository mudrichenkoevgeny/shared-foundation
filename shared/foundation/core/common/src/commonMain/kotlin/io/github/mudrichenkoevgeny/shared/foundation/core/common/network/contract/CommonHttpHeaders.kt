package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Common HTTP header names used for network communication and request tracing.
 */
object CommonHttpHeaders {
    /** Request or response header carrying a distributed trace identifier. */
    const val TRACE_HEADER_NAME = "X-Trace-Id"

    /** Header for the client type (aligned with [ClientType] wire values). */
    const val CLIENT_TYPE_HEADER_NAME = "X-Client-Type"

    /** Header for a stable device identifier. */
    const val DEVICE_ID_HEADER_NAME = "X-Device-Id"

    /** Header for a human-readable device name. */
    const val DEVICE_NAME_HEADER_NAME = "X-Device-Name"

    /** Header for the client application version. */
    const val APP_VERSION_HEADER_NAME = "X-App-Version"

    /** Header for the client operating system version. */
    const val OPERATION_SYSTEM_VERSION_HEADER_NAME = "X-OS-Version"

    /** Header for the negotiated client API version. */
    const val API_VERSION_HEADER_NAME = "X-Api-Version"
}
