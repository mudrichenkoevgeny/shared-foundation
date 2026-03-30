package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A page of items from a larger, filtered listing.
 *
 * Shared by HTTP APIs ([Serializable] JSON shape), server domain/services, and clients. [items], [totalCount],
 * [pageNumber], [pageSize], and [totalPages] use the same wire names as [CommonApiFields] and [ListingParamNames.Pagination]
 * so list requests and responses stay aligned. [pageNumber] is **one-based** (`1` = first page), matching [ListingParamNames.Pagination.PAGE_NUMBER].
 * [totalPages] is typically computed by the producer so consumers do not reimplement edge cases for empty results or the last partial page.
 *
 * @param T Element type of [items]; must have a matching [kotlinx.serialization.KSerializer] at the call site when
 *   encoding or decoding JSON.
 * @property items Records for the current page (may be shorter than [pageSize] on the last page).
 * @property totalCount Total number of items matching the query across all pages.
 * @property pageNumber One-based index of this page (`1` is the first page; same semantics as [ListingParamNames.Pagination.PAGE_NUMBER]).
 * @property pageSize Maximum number of items per page for this response (same key as
 *   [ListingParamNames.Pagination.PAGE_SIZE]).
 * @property totalPages Number of pages for [totalCount] and [pageSize]; `0` when [pageSize] is not positive.
 */
@Serializable
data class PagedResult<T>(
    @SerialName(CommonApiFields.ITEMS)
    val items: List<T>,

    @SerialName(CommonApiFields.TOTAL_COUNT)
    val totalCount: Long,

    @SerialName(ListingParamNames.Pagination.PAGE_NUMBER)
    val pageNumber: Int,

    @SerialName(ListingParamNames.Pagination.PAGE_SIZE)
    val pageSize: Int,

    @SerialName(CommonApiFields.TOTAL_PAGES)
    val totalPages: Long
)
