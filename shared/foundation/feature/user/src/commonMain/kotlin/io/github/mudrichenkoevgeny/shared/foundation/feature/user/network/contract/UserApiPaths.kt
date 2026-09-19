package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

/**
 * User-specific naming conventions for API path parameters.
 */
object UserApiPaths {
    /**
     * Path parameter for a single user identifier.
     *
     * Wire format is a UUID string (hex with dashes).
     */
    const val USER_ID = "user_id"

    /**
     * Path parameter for a single user session identifier.
     *
     * Wire format is a UUID string (hex with dashes).
     */
    const val SESSION_ID = "session_id"

    /**
     * Path parameter for a single user identifier record ID (e.g., email or phone binding identifier).
     *
     * Wire format is a UUID string (hex with dashes).
     */
    const val USER_IDENTIFIER_ID = "user_identifier_id"
}