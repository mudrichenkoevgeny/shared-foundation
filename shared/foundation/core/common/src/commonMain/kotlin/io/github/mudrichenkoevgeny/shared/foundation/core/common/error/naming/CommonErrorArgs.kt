package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse

/**
 * Common keys for error arguments used in API responses and backend logging.
 *
 * Values appear under [ApiErrorResponse.args] (public) or are logged internally (secret).
 */
object CommonErrorArgs {
    /**
     * Key for an additional human-readable detail string.
     * Associated with [CommonErrorCodes.UNKNOWN], [CommonErrorCodes.INTERNAL],
     * [CommonErrorCodes.SERVICE_UNAVAILABLE], [CommonErrorCodes.BAD_REQUEST],
     * [CommonErrorCodes.INVALID_JSON_BODY].
     */
    const val MESSAGE = "message"

    /**
     * Key for the name of a query or path parameter that caused the error.
     * Associated with [CommonErrorCodes.MISSING_REQUIRED_PARAMETER],
     * [CommonErrorCodes.INVALID_PARAMETER_VALUE].
     */
    const val PARAMETER_NAME = "parameterName"

    /**
     * Key for the JSON field name that caused the error.
     * Associated with [CommonErrorCodes.MISSING_REQUIRED_FIELD],
     * [CommonErrorCodes.BLANK_STRING_FIELD], [CommonErrorCodes.EMPTY_COLLECTION_FIELD],
     * [CommonErrorCodes.INVALID_FIELD_VALUE].
     */
    const val FIELD_NAME = "fieldName"

    /**
     * Key for seconds until the client may retry.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    const val RETRY_AFTER_SECONDS = "retryAfterSeconds"

    /**
     * Key for an application-specific rate-limit action discriminator.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    const val RATE_LIMIT_ACTION_CODE = "rateLimitActionCode"

    /**
     * Key for a numeric limit that was exceeded.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    const val LIMIT = "limit"

    /**
     * Key for an identifier related to the error (resource ID, trace ID, etc.).
     * Associated with [CommonErrorCodes.NOT_FOUND], [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    const val IDENTIFIER = "identifier"

    /**
     * Key for the logical resource type related to the error.
     * Associated with [CommonErrorCodes.NOT_FOUND].
     */
    const val RESOURCE = "resource"
}