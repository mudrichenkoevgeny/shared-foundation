package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.client.naming

/**
 * Machine-readable client-side security error code string constants.
 *
 * Raised by client applications or SDK security managers.
 * **NOT returned by backend API responses.**
 */
object ClientSecurityErrorCodes {

    /**
     * Password policy is missing, corrupted, or cannot be fetched from the backend.
     */
    const val PASSWORD_POLICY_UNAVAILABLE = "PASSWORD_POLICY_UNAVAILABLE"
}
