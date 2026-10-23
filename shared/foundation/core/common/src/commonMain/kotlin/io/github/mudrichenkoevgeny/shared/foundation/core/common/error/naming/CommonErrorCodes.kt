package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

/**
 * Common error codes for API responses and backend logging, used to categorize failures.
 */
object CommonErrorCodes {
    /**
     * Failure that does not match a more specific code.
     *
     * **Localization keys:**
     * - `unknown`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Internal description of the unknown error.
     */
    const val UNKNOWN = "UNKNOWN"

    /**
     * Internal error caused by an unhandled exception.
     *
     * **Localization keys:**
     * - `internal`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: The exception message.
     */
    const val INTERNAL = "INTERNAL"

    /**
     * Requested object was not found.
     *
     * **Localization keys:**
     * - `not_found_without_args`: Static fallback message.
     * - `not_found_with_args`: Parameterized message template (`{resource}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.RESOURCE]: Logical resource name or entity label (`{resource}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.RESOURCE]: Logical resource name (e.g., "User").
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.IDENTIFIER]: The specific ID that was not found.
     */
    const val NOT_FOUND = "NOT_FOUND"

    /**
     * Service or dependency temporarily unavailable.
     *
     * **Localization keys:**
     * - `service_unavailable`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Details about the unavailable service.
     */
    const val SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE"

    /**
     * Client exceeded a rate limit for a specific action.
     *
     * **Localization keys:**
     * - `too_many_requests_without_args`: Static fallback message.
     * - `too_many_requests_with_args`: Parameterized message template (`{retryAfterSeconds}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Wait time before retrying in seconds (`{retryAfterSeconds}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Wait time before retrying.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.RATE_LIMIT_ACTION_CODE]: Identifier of the limited action.
     * - [CommonErrorArgs.LIMIT]: The threshold that was exceeded.
     * - [CommonErrorArgs.IDENTIFIER]: Client or user identifier.
     */
    const val TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS"

    /**
     * A required query or path parameter was absent.
     *
     * **Localization keys:**
     * - `missing_field_without_args`: Static fallback message.
     * - `missing_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the missing parameter (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the missing parameter.
     */
    const val MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER"

    /**
     * A parameter was present but its value was invalid.
     *
     * **Localization keys:**
     * - `invalid_field_without_args`: Static fallback message.
     * - `invalid_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the invalid parameter (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the invalid parameter.
     */
    const val INVALID_PARAMETER_VALUE = "INVALID_PARAMETER_VALUE"

    /**
     * A required JSON field was absent from the request body.
     *
     * **Localization keys:**
     * - `missing_field_without_args`: Static fallback message.
     * - `missing_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the missing field (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the missing field.
     */
    const val MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD"

    /**
     * A string field was present but blank or whitespace-only.
     *
     * **Localization keys:**
     * - `blank_field_without_args`: Static fallback message.
     * - `blank_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the blank field (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the blank field.
     */
    const val BLANK_STRING_FIELD = "BLANK_STRING_FIELD"

    /**
     * A collection field was present but empty.
     *
     * **Localization keys:**
     * - `missing_field_without_args`: Static fallback message.
     * - `missing_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the empty collection field (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the empty collection field.
     */
    const val EMPTY_COLLECTION_FIELD = "EMPTY_COLLECTION_FIELD"

    /**
     * A field value fails validation (format, range, or business rule).
     *
     * **Localization keys:**
     * - `invalid_field_without_args`: Static fallback message.
     * - `invalid_field_with_args`: Parameterized message template (`{fieldName}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the invalid field (`{fieldName}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the invalid field.
     */
    const val INVALID_FIELD_VALUE = "INVALID_FIELD_VALUE"

    /**
     * Generic client error that does not fit a more specific code.
     *
     * **Localization keys:**
     * - `bad_request`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Internal description of the bad request.
     */
    const val BAD_REQUEST = "BAD_REQUEST"

    /**
     * Request body was not valid JSON or could not be parsed as expected.
     *
     * **Localization keys:**
     * - `bad_request`
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Parse or schema error details.
     */
    const val INVALID_JSON_BODY = "INVALID_JSON_BODY"
}
