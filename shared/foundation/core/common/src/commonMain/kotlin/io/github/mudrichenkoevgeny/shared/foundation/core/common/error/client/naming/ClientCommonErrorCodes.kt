package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.client.naming

/**
 * Common client-side / SDK error code string constants.
 *
 * Raised by client applications or SDK transport/lifecycle layers before or outside backend API calls.
 * **NOT returned by backend API responses.**
 */
public object ClientCommonErrorCodes {
    /**
     * Error code indicating no internet connection available on the client device.
     *
     * **Localization keys:**
     * - `no_internet`
     */
    public const val NO_INTERNET_CONNECTION: String = "NO_INTERNET_CONNECTION"

    /**
     * Error code indicating generic network transport failure (e.g. timeout, DNS resolution failure).
     *
     * **Localization keys:**
     * - `network`
     */
    public const val NETWORK: String = "NETWORK"

    /**
     * Error code indicating response payload contract violation (e.g. deserialization failure).
     *
     * **Localization keys:**
     * - `bad_request`
     */
    public const val CONTRACT_VIOLATION: String = "CONTRACT_VIOLATION"

    /**
     * Error code indicating component or screen lifecycle state error.
     *
     * **Localization keys:**
     * - `internal`
     */
    public const val LIFECYCLE_ERROR: String = "LIFECYCLE_ERROR"
}
