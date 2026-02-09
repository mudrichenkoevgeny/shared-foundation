package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming

/**
 * User-specific error codes for API responses and backend logging, used to categorize failures.
 */
object UserErrorCodes {
    // token
    const val INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN"
    const val ACCESS_TOKEN_EXPIRED = "ACCESS_TOKEN_EXPIRED"
    const val INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN"
    // user
    const val INVALID_SESSION = "INVALID_SESSION"
    const val USER_BLOCKED = "USER_BLOCKED"
    const val USER_READ_ONLY = "USER_READ_ONLY"
    const val USER_FORBIDDEN = "USER_FORBIDDEN"
    const val USER_NOT_FOUND = "USER_NOT_FOUND"
    // all identifiers
    const val INVALID_CREDENTIALS = "INVALID_CREDENTIALS"
    const val CAN_NOT_DELETE_USER_IDENTIFIER = "CAN_NOT_DELETE_USER_IDENTIFIER"
    const val CAN_NOT_CREATE_USER_IDENTIFIER = "CAN_NOT_CREATE_USER_IDENTIFIER"
    const val ALREADY_HAS_USER_IDENTIFIER_WITH_THAT_TYPE = "ALREADY_HAS_USER_IDENTIFIER_WITH_THAT_TYPE"
    const val AUTHENTICATION_CONFIRMATION_REQUIRED = "AUTHENTICATION_CONFIRMATION_REQUIRED"
    // internal identifiers
    const val WRONG_PASSWORD = "WRONG_PASSWORD"
    const val PASSWORD_TOO_WEAK = "PASSWORD_TOO_WEAK"
    const val WRONG_CONFIRMATION_CODE = "WRONG_CONFIRMATION_CODE"
    // external identifiers
    const val EXTERNAL_ID_MISMATCH = "EXTERNAL_ID_MISMATCH"
    const val EXTERNAL_TOKEN_INVALID = "EXTERNAL_TOKEN_INVALID"
}