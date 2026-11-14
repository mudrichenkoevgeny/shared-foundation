package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse

/**
 * Common keys for error arguments used in API responses and backend logging.
 *
 * Values appear under [ApiErrorResponse.args] (public) or are logged internally (secret).
 */
public object CommonErrorArgs {
    /**
     * Key for an additional human-readable detail string.
     * Associated with [CommonErrorCodes.UNKNOWN], [CommonErrorCodes.INTERNAL],
     * [CommonErrorCodes.SERVICE_UNAVAILABLE], [CommonErrorCodes.BAD_REQUEST],
     * [CommonErrorCodes.INVALID_JSON_BODY].
     */
    public const val MESSAGE: String = "message"

    /**
     * Key for the name of a query or path parameter that caused the error.
     * Associated with [CommonErrorCodes.MISSING_REQUIRED_PARAMETER],
     * [CommonErrorCodes.INVALID_PARAMETER_VALUE].
     */
    public const val PARAMETER_NAME: String = "parameterName"

    /**
     * Key for the JSON field name that caused the error.
     * Associated with [CommonErrorCodes.MISSING_REQUIRED_FIELD],
     * [CommonErrorCodes.BLANK_STRING_FIELD], [CommonErrorCodes.EMPTY_COLLECTION_FIELD],
     * [CommonErrorCodes.INVALID_FIELD_VALUE].
     */
    public const val FIELD_NAME: String = "fieldName"

    /**
     * Key for seconds until the client may retry.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    public const val RETRY_AFTER_SECONDS: String = "retryAfterSeconds"

    /**
     * Key for an application-specific rate-limit action discriminator.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    public const val RATE_LIMIT_ACTION_CODE: String = "rateLimitActionCode"

    /**
     * Key for a numeric limit that was exceeded.
     * Associated with [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    public const val LIMIT: String = "limit"

    /**
     * Key for an identifier related to the error (resource ID, trace ID, etc.).
     * Associated with [CommonErrorCodes.NOT_FOUND], [CommonErrorCodes.TOO_MANY_REQUESTS].
     */
    public const val IDENTIFIER: String = "identifier"

    /**
     * Key for the logical resource type related to the error.
     * Associated with [CommonErrorCodes.NOT_FOUND].
     */
    public const val RESOURCE: String = "resource"
}
