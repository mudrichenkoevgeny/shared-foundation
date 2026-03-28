package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generic JSON wrapper for offset-paged list endpoints.
 *
 * [page] and [pageSize] use the same wire names as [CommonApiQueryParams.PaginationQueryParams] so request query and
 * response body stay aligned. [totalPages] is produced by the server so clients do not reimplement edge cases for empty
 * results or the last partial page.
 *
 * @param T Element type of [items]; must have a matching [kotlinx.serialization.KSerializer] at the call site.
 * @property items Records for the current page (may be shorter than [pageSize] on the last page).
 * @property totalCount Total number of items matching the query across all pages.
 * @property page Zero-based index of this page (same semantics as [CommonApiQueryParams.PaginationQueryParams.PAGE]).
 * @property pageSize Maximum number of items per page for this response (same wire name as
 *   [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE]).
 * @property totalPages Number of pages for [totalCount] and [pageSize]; `0` when [pageSize] is not positive.
 */
@Serializable
data class PagedResponse<T>(
    @SerialName(CommonApiFields.ITEMS)
    val items: List<T>,

    @SerialName(CommonApiFields.TOTAL_COUNT)
    val totalCount: Long,

    @SerialName(CommonApiQueryParams.PaginationQueryParams.PAGE)
    val page: Int,

    @SerialName(CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE)
    val pageSize: Int,

    @SerialName(CommonApiFields.TOTAL_PAGES)
    val totalPages: Long
)