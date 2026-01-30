package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.constants

/**
 * Common error codes for API responses and backend logging, used to categorize failures.
 */
object CommonErrorCodes {
    const val UNKNOWN = "UNKNOWN"
    const val INTERNAL = "INTERNAL"
    const val SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE"
    const val TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS"
    const val MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER"
    const val INVALID_PARAMETER_VALUE = "INVALID_PARAMETER_VALUE"
    const val MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD"
    const val BLANK_STRING_FIELD = "BLANK_STRING_FIELD"
    const val EMPTY_COLLECTION_FIELD = "EMPTY_COLLECTION_FIELD"
    const val INVALID_FIELD_VALUE = "INVALID_FIELD_VALUE"
    const val BAD_REQUEST = "BAD_REQUEST"
    const val INVALID_JSON_BODY = "INVALID_JSON_BODY"
}