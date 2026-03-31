package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

/**
 * Common error codes for API responses and backend logging, used to categorize failures.
 */
object CommonErrorCodes {
    /** Failure that does not match a more specific code. */
    const val UNKNOWN = "UNKNOWN"

    /** Unexpected server-side failure. */
    const val INTERNAL = "INTERNAL"

    /** Requested object was not found. */
    const val NOT_FOUND = "NOT_FOUND"

    /** Service temporarily unavailable (e.g. overload or dependency down). */
    const val SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE"

    /** Client exceeded a rate limit; see [CommonErrorArgs.RETRY_AFTER_SECONDS] and related args. */
    const val TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS"

    /** A required query or path parameter was absent. */
    const val MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER"

    /** A parameter was present but its value was invalid. */
    const val INVALID_PARAMETER_VALUE = "INVALID_PARAMETER_VALUE"

    /** A required JSON field was absent from the request body. */
    const val MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD"

    /** A string field was present but blank or whitespace-only. */
    const val BLANK_STRING_FIELD = "BLANK_STRING_FIELD"

    /** A collection field was present but empty. */
    const val EMPTY_COLLECTION_FIELD = "EMPTY_COLLECTION_FIELD"

    /** A field was present but its value failed validation. */
    const val INVALID_FIELD_VALUE = "INVALID_FIELD_VALUE"

    /** Generic client error that does not fit a more specific code. */
    const val BAD_REQUEST = "BAD_REQUEST"

    /** Request body was not valid JSON or could not be parsed as expected. */
    const val INVALID_JSON_BODY = "INVALID_JSON_BODY"
}
