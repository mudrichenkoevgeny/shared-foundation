package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

object CommonApiQueryParams {
    object PaginationQueryParams {
        /** Zero-based page index for list endpoints. */
        const val PAGE = "page"

        /** Page size (items per page) for list endpoints. */
        const val PAGE_SIZE = "page_size"
    }

    object SortQueryParams {
        /** Sort field name for list endpoints. */
        const val SORT_BY = "sort_by"

        /** Sort direction for list endpoints (see [CommonApiQueryParamValues.SortOrder]) */
        const val SORT_ORDER = "sort_order"
    }
}