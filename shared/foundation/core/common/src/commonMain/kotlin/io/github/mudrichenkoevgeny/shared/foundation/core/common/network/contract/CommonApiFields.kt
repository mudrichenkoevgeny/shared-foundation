package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes

/**
 * Common naming conventions for API request parameters, response fields, and shared listing wire values (for example
 * `asc` / `desc` via the nested [SortOrder] object).
 */
public object CommonApiFields {
    /** JSON field name for the `items` array in a paginated list response body. */
    public const val ITEMS: String = "items"

    /** JSON field name for total number of items across all pages in a paged listing. */
    public const val TOTAL_COUNT: String = "total_count"

    /** JSON field name for total number of pages in a paged listing (server-computed). */
    public const val TOTAL_PAGES: String = "total_pages"

    /** JSON field name for a unique resource or message identifier. */
    public const val ID: String = "id"

    /** JSON field name for resource creation timestamp. */
    public const val CREATED_AT: String = "created_at"

    /** JSON field name for resource last-update timestamp. */
    public const val UPDATED_AT: String = "updated_at"

    /** JSON field name for a machine-readable error code (see [CommonErrorCodes]). */
    public const val CODE: String = "code"

    /** JSON field name for a human-readable error message. */
    public const val MESSAGE: String = "message"

    /** JSON field name for structured error metadata as string key-value pairs. */
    public const val ARGS: String = "args"

    /** JSON field name for a message or event type discriminator. */
    public const val TYPE: String = "type"

    /** JSON field name for an optional nested payload (e.g. WebSocket frame body). */
    public const val PAYLOAD: String = "payload"

    /** JSON field name for auxiliary string key-value metadata. */
    public const val METADATA: String = "metadata"

    /** JSON field name for a millisecond timestamp. */
    public const val TIMESTAMP: String = "timestamp"

    /** JSON field name for the client category (e.g. web, android). */
    public const val CLIENT_TYPE: String = "client_type"

    /** JSON field name for nested client device info payload. */
    public const val CLIENT_DEVICE_INFO: String = "client_device_info"

    /** JSON field name for nested client info payload. */
    public const val CLIENT_INFO: String = "client_info"

    /** JSON field name for the HTTP User-Agent or equivalent. */
    public const val USER_AGENT: String = "user_agent"

    /** JSON field name for the client IP address. */
    public const val IP_ADDRESS: String = "ip_address"

    /** JSON field name for the masked client IP address. */
    public const val MASKED_IP_ADDRESS: String = "masked_ip_address"

    /** JSON field name for the user preferred language tag. */
    public const val LANGUAGE: String = "language"

    /** JSON field name for a stable device identifier. */
    public const val DEVICE_ID: String = "device_id"

    /** JSON field name for a human-readable device name. */
    public const val DEVICE_NAME: String = "device_name"

    /** JSON field name for the client application version. */
    public const val APP_VERSION: String = "app_version"

    /** JSON field name for the client operating system version string. */
    public const val OPERATION_SYSTEM_VERSION: String = "operation_system_version"

    /** JSON field name for the negotiated client API version. */
    public const val API_VERSION: String = "api_version"

    /** `true` if sensitive fields in the same body are masked/redacted; `false` if the server returned raw values allowed for the caller. */
    public const val IS_SENSITIVE_VALUES_MASKED: String = "is_sensitive_values_masked"

    /**
     * Wire values for list `sort_order` parameters (and any matching internal representation).
     */
    public object SortOrder {
        /** Ascending order (`asc`). */
        public const val ASC: String = "asc"

        /** Descending order (`desc`). */
        public const val DESC: String = "desc"
    }
}
