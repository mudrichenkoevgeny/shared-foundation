package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.client.naming

/**
 * Stable string codes for client-side errors raised before or outside backend API user calls.
 *
 * Raised by client applications or SDK user auth managers.
 * **NOT returned by backend API responses.**
 */
public object ClientUserErrorCodes {
    /**
     * External authentication (OAuth, Google, Apple) was cancelled by the user.
     *
     * **Localization keys:**
     * - `external_auth_cancelled`
     */
    public const val EXTERNAL_AUTH_CANCELLED: String = "EXTERNAL_AUTH_CANCELLED"

    /**
     * External authentication (OAuth, Google, Apple) failed on the client side.
     *
     * **Localization keys:**
     * - `external_auth_failed`
     */
    public const val EXTERNAL_AUTH_FAILED: String = "EXTERNAL_AUTH_FAILED"

    /**
     * Client-side throttling error indicating too many confirmation requests initiated locally.
     *
     * **Localization keys:**
     * - `too_many_confirmation_requests`
     */
    public const val TOO_MANY_CONFIRMATION_REQUESTS: String = "TOO_MANY_CONFIRMATION_REQUESTS"
}
