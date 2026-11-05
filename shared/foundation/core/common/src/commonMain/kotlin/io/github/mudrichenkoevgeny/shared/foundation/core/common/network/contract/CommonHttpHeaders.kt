package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Common HTTP header names used for network communication and request tracing.
 */
public object CommonHttpHeaders {
    /** Request or response header carrying a distributed trace identifier. */
    public const val TRACE_HEADER_NAME: String = "X-Trace-Id"

    /** Header for the client type (aligned with [ClientType] wire values). */
    public const val CLIENT_TYPE_HEADER_NAME: String = "X-Client-Type"

    /** Header for a stable device identifier. */
    public const val DEVICE_ID_HEADER_NAME: String = "X-Device-Id"

    /** Header for a human-readable device name. */
    public const val DEVICE_NAME_HEADER_NAME: String = "X-Device-Name"

    /** Header for the client application version. */
    public const val APP_VERSION_HEADER_NAME: String = "X-App-Version"

    /** Header for the client operating system version. */
    public const val OPERATION_SYSTEM_VERSION_HEADER_NAME: String = "X-OS-Version"

    /** Header for the negotiated client API version. */
    public const val API_VERSION_HEADER_NAME: String = "X-Api-Version"
}
