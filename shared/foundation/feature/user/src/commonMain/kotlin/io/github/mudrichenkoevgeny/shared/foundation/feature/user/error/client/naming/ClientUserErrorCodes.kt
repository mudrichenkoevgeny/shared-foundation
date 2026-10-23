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
     *
     * **Localization keys:**
     * - `external_auth_cancelled`
     */
    const val EXTERNAL_AUTH_CANCELLED = "EXTERNAL_AUTH_CANCELLED"

    /**
     * External authentication (OAuth, Google, Apple) failed on the client side.
     *
     * **Localization keys:**
     * - `external_auth_failed`
     */
    const val EXTERNAL_AUTH_FAILED = "EXTERNAL_AUTH_FAILED"

    /**
     * Client-side throttling error indicating too many confirmation requests initiated locally.
     *
     * **Localization keys:**
     * - `too_many_confirmation_requests`
     */
    const val TOO_MANY_CONFIRMATION_REQUESTS = "TOO_MANY_CONFIRMATION_REQUESTS"
}
