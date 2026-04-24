package io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming

/**
 * Common error codes for API responses and backend logging, used to categorize failures.
 */
object CommonErrorCodes {
    /** * Failure that does not match a more specific code.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Internal description of the unknown error.
     */
    const val UNKNOWN = "UNKNOWN"

    /** * Internal error caused by an unhandled exception.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: The exception message.
     */
    const val INTERNAL = "INTERNAL"

    /** * Requested object was not found.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.RESOURCE]: Logical resource name (e.g., "User").
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.IDENTIFIER]: The specific ID that was not found.
     */
    const val NOT_FOUND = "NOT_FOUND"

    /** * Service or dependency temporarily unavailable.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Details about the unavailable service.
     */
    const val SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE"

    /** * Client exceeded a rate limit for a specific action.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.RETRY_AFTER_SECONDS]: Wait time before retrying.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.RATE_LIMIT_ACTION_CODE]: Identifier of the limited action.
     * - [CommonErrorArgs.LIMIT]: The threshold that was exceeded.
     * - [CommonErrorArgs.IDENTIFIER]: Client or user identifier.
     */
    const val TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS"

    /** * A required query or path parameter was absent.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the missing parameter.
     */
    const val MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER"

    /** * A parameter was present but its value was invalid.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.PARAMETER_NAME]: Name of the invalid parameter.
     */
    const val INVALID_PARAMETER_VALUE = "INVALID_PARAMETER_VALUE"

    /** * A required JSON field was absent from the request body.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the missing field.
     */
    const val MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD"

    /** * A string field was present but blank or whitespace-only.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the blank field.
     */
    const val BLANK_STRING_FIELD = "BLANK_STRING_FIELD"

    /** * A collection field was present but empty.
     *
     * **Public arguments:**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the empty collection field.
     */
    const val EMPTY_COLLECTION_FIELD = "EMPTY_COLLECTION_FIELD"

    /** * A field value fails validation (format, range, or business rule).
     *
     * **Public arguments:**
     * - [CommonErrorArgs.FIELD_NAME]: Name of the invalid field.
     */
    const val INVALID_FIELD_VALUE = "INVALID_FIELD_VALUE"

    /** * Generic client error that does not fit a more specific code.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Internal description of the bad request.
     */
    const val BAD_REQUEST = "BAD_REQUEST"

    /** * Request body was not valid JSON or could not be parsed as expected.
     *
     * **Secret arguments:**
     * - [CommonErrorArgs.MESSAGE]: Parse or schema error details.
     */
    const val INVALID_JSON_BODY = "INVALID_JSON_BODY"
}