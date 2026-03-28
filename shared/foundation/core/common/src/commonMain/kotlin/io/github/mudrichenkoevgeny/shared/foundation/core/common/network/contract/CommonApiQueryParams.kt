package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * Shared query parameter **names** for list-style HTTP endpoints (pagination and sort).
 *
 * Allowed **values** for [SortQueryParams.SORT_BY] and [SortQueryParams.SORT_ORDER] are resource-specific; common
 * baselines live in [CommonApiQueryParamValues] (for example [CommonApiQueryParamValues.TimestampSortBy] and
 * [CommonApiQueryParamValues.SortOrder]).
 */
object CommonApiQueryParams {
    /**
     * Names for numeric pagination of list results.
     */
    object PaginationQueryParams {
        /**
         * Zero-based page index.
         */
        const val PAGE = "page"

        /**
         * Page size (maximum items returned per page).
         */
        const val PAGE_SIZE = "page_size"
    }

    /**
     * Names for ordering list results.
     */
    object SortQueryParams {
        /**
         * Field to sort by (`sort_by`). Wire value is usually a stable field key such as
         * [CommonApiQueryParamValues.TimestampSortBy.CREATED_AT] or [CommonApiQueryParamValues.TimestampSortBy.UPDATED_AT];
         * feature modules may define additional allowed values (for example session fields).
         */
        const val SORT_BY = "sort_by"

        /**
         * Sort direction (`sort_order`). Allowed wire values: [CommonApiQueryParamValues.SortOrder.ASC] or
         * [CommonApiQueryParamValues.SortOrder.DESC].
         */
        const val SORT_ORDER = "sort_order"
    }
}