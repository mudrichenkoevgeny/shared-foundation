package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes

/**
 * Common naming conventions for API request parameters, response fields, and shared listing wire values (for example
 * `asc` / `desc` via the nested [SortOrder] object).
 */
object CommonApiFields {
    /** JSON field name for the `items` array in a paginated list response body. */
    const val ITEMS = "items"

    /** JSON field name for total number of items across all pages in a paged listing. */
    const val TOTAL_COUNT = "total_count"

    /** JSON field name for total number of pages in a paged listing (server-computed). */
    const val TOTAL_PAGES = "total_pages"

    /** JSON field name for a unique resource or message identifier. */
    const val ID = "id"

    /** JSON field name for resource creation timestamp. */
    const val CREATED_AT = "created_at"

    /** JSON field name for resource last-update timestamp. */
    const val UPDATED_AT = "updated_at"

    /** JSON field name for a machine-readable error code (see [CommonErrorCodes]). */
    const val CODE = "code"

    /** JSON field name for a human-readable error message. */
    const val MESSAGE = "message"

    /** JSON field name for structured error metadata as string key-value pairs. */
    const val ARGS = "args"

    /** JSON field name for a message or event type discriminator. */
    const val TYPE = "type"

    /** JSON field name for an optional nested payload (e.g. WebSocket frame body). */
    const val PAYLOAD = "payload"

    /** JSON field name for auxiliary string key-value metadata. */
    const val METADATA = "metadata"

    /** JSON field name for a millisecond timestamp. */
    const val TIMESTAMP = "timestamp"

    /** JSON field name for the client category (e.g. web, android). */
    const val CLIENT_TYPE = "client_type"

    /** JSON field name for the HTTP User-Agent or equivalent. */
    const val USER_AGENT = "user_agent"

    /** JSON field name for the client IP address. */
    const val IP_ADDRESS = "ip_address"

    /** JSON field name for the masked client IP address. */
    const val MASKED_IP_ADDRESS = "masked_ip_address"

    /** JSON field name for the user preferred language tag. */
    const val LANGUAGE = "language"

    /** JSON field name for a stable device identifier. */
    const val DEVICE_ID = "device_id"

    /** JSON field name for a human-readable device name. */
    const val DEVICE_NAME = "device_name"

    /** JSON field name for the client application version. */
    const val APP_VERSION = "app_version"

    /** JSON field name for the client operating system version string. */
    const val OPERATION_SYSTEM_VERSION = "operation_system_version"

    /** JSON field name for the negotiated client API version. */
    const val API_VERSION = "api_version"

    /** `true` if sensitive fields in the same body are masked/redacted; `false` if the server returned raw values allowed for the caller. */
    const val IS_SENSITIVE_VALUES_MASKED = "is_sensitive_values_masked"

    /**
     * Wire values for list `sort_order` parameters (and any matching internal representation).
     */
    object SortOrder {
        /** Ascending order (`asc`). */
        const val ASC = "asc"

        /** Descending order (`desc`). */
        const val DESC = "desc"
    }
}
