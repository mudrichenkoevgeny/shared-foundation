package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult

/**
 * Creates a test [PagedResult] instance with auto-calculated pagination properties.
 *
 * @param items List of result items.
 * @param totalCount Total count of matching records.
 * @param pageNumber 1-based page index.
 * @param pageSize Number of items per page.
 * @param totalPages Total calculated page count.
 */
fun <T> createTestPagedResult(
    items: List<T> = emptyList(),
    totalCount: Long = items.size.toLong(),
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    totalPages: Long = calculateTotalPages(totalCount, pageSize)
) = PagedResult(
    items = items,
    totalCount = totalCount,
    pageNumber = pageNumber,
    pageSize = pageSize,
    totalPages = totalPages
)

private fun calculateTotalPages(totalCount: Long, pageSize: Int): Long {
    if (totalCount <= 0L) return 0L
    if (pageSize <= 0) return 0L
    return (totalCount + pageSize - 1) / pageSize
}
