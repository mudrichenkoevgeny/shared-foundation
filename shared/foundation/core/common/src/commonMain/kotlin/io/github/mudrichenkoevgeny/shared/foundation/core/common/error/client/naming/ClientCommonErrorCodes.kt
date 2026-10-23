package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.client.naming

/**
 * Common client-side / SDK error code string constants.
 *
 * Raised by client applications or SDK transport/lifecycle layers before or outside backend API calls.
 * **NOT returned by backend API responses.**
 */
object ClientCommonErrorCodes {

    /**
     * Error code indicating no internet connection available on the client device.
     */
    const val NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION"

    /**
     * Error code indicating generic network transport failure (e.g. timeout, DNS resolution failure).
     */
    const val NETWORK = "NETWORK"

    /**
     * Error code indicating response payload contract violation (e.g. deserialization failure).
     */
    const val CONTRACT_VIOLATION = "CONTRACT_VIOLATION"

    /**
     * Error code indicating component or screen lifecycle state error.
     */
    const val LIFECYCLE_ERROR = "LIFECYCLE_ERROR"
}
