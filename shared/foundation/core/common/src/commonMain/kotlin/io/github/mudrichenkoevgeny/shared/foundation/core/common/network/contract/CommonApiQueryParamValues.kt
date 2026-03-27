package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

object CommonApiQueryParamValues {
    /**
     * Supported `sort_by` values for timestamp-based fields.
     */
    object TimestampSortBy {
        /** Sort by [CommonApiFields.CREATED_AT]. */
        const val CREATED_AT = CommonApiFields.CREATED_AT

        /** Sort by [CommonApiFields.UPDATED_AT]. */
        const val UPDATED_AT = CommonApiFields.UPDATED_AT
    }

    /**
     * Supported ordering direction values for list endpoints.
     */
    object SortOrder {
        /** Ascending order. */
        const val ASC = "asc"

        /** Descending order. */
        const val DESC = "desc"
    }
}