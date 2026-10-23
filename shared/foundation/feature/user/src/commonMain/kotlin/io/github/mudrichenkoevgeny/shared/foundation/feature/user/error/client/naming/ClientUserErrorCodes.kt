package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.client.naming

/**
 * Stable string codes for client-side errors raised before or outside backend API user calls.
 *
 * Raised by client applications or SDK user auth managers.
 * **NOT returned by backend API responses.**
 */
object ClientUserErrorCodes {

    /**
     * External authentication (OAuth, Google, Apple) was cancelled by the user.
     */
    const val EXTERNAL_AUTH_CANCELLED = "EXTERNAL_AUTH_CANCELLED"

    /**
     * External authentication (OAuth, Google, Apple) failed on the client side.
     */
    const val EXTERNAL_AUTH_FAILED = "EXTERNAL_AUTH_FAILED"

    /**
     * Client-side throttling error indicating too many confirmation requests initiated locally.
     */
    const val TOO_MANY_CONFIRMATION_REQUESTS = "TOO_MANY_CONFIRMATION_REQUESTS"

    /**
     * Client-side validation error indicating self-service registration is disabled by platform policy.
     */
    const val REGISTRATION_DISABLED = "REGISTRATION_DISABLED"
}
