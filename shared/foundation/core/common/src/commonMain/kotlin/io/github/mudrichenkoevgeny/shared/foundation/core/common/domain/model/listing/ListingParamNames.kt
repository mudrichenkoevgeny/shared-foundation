package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

/**
 * Stable **keys** for paginated and sorted listings shared by backend, clients, and JSON field names where applicable.
 *
 * Use these constants when building HTTP query strings, parsing list requests, mapping to persistence (for example
 * `LIMIT`/`OFFSET` with **one-based** page index: offset = `(page - 1) * pageSize` when the page size is positive), or
 * any other contract surface that must stay aligned across processes.
 *
 * Allowed **values** for [Sort.SORT_BY] and [Sort.SORT_ORDER] are resource-specific; common baselines live in
 * [CommonSortValues] (for example [CommonSortValues.TimestampSortBy]) and [SortOrder].
 */
object ListingParamNames {
    /**
     * Keys for page selection in list results (for example HTTP query parameters). Indexing is **one-based** (`1` = first page); see [PAGE_NUMBER].
     */
    object Pagination {
        /**
         * One-based page index (`1` is the first page).
         */
        const val PAGE_NUMBER = "page_number"

        /**
         * Page size (maximum items returned per page).
         */
        const val PAGE_SIZE = "page_size"
    }

    /**
     * Keys for ordering list results (for example HTTP query parameters).
     */
    object Sort {
        /**
         * Field to sort by (`sort_by`).
         */
        const val SORT_BY = "sort_by"

        /**
         * Sort direction (`sort_order`). Allowed wire values match [SortOrder]: [SortOrder.ASC] or [SortOrder.DESC].
         */
        const val SORT_ORDER = "sort_order"
    }
}
