package io.github.mudrichenkoevgeny.shared.foundation.core.common.constants

/**
 * Common HTTP header names used for network communication and request tracing.
 */
object CommonNetworkFoundationConstants {
    const val TRACE_HEADER_NAME = "X-Trace-Id"
    const val CLIENT_TYPE_HEADER_NAME = "X-Client-Type"
    const val DEVICE_ID_HEADER_NAME = "X-Device-Id"
    const val DEVICE_NAME_HEADER_NAME = "X-Device-Name"
    const val APP_VERSION_HEADER_NAME = "X-App-Version"
    const val OPERATION_SYSTEM_VERSION_HEADER_NAME = "X-OS-Version"
}