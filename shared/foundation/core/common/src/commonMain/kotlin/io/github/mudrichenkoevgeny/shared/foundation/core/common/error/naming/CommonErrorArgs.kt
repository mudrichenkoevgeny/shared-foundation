package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse

/**
 * Common keys for error arguments used in API responses and backend logging.
 *
 * Values appear under [ApiErrorResponse.args].
 */
object CommonErrorArgs {
    /** Key for an additional human-readable detail string. */
    const val MESSAGE = "message"

    /** Key for the name of a query or path parameter that caused the error. */
    const val PARAMETER_NAME = "parameterName"

    /** Key for the JSON field name that caused the error. */
    const val FIELD_NAME = "fieldName"

    /** Key for seconds until the client may retry (e.g. after rate limiting). */
    const val RETRY_AFTER_SECONDS = "retryAfterSeconds"

    /** Key for an application-specific rate-limit action discriminator. */
    const val RATE_LIMIT_ACTION_CODE = "rateLimitActionCode"

    /** Key for a numeric limit that was exceeded (e.g. max length). */
    const val LIMIT = "limit"

    /** Key for an identifier related to the error (resource id, token id, etc.). */
    const val IDENTIFIER = "identifier"
}
