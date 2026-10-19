package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult

fun <T> createTestPagedResult(
    items: List<T> = emptyList(),
    totalCount: Long = items.size.toLong(),
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    totalPages: Long = if (items.isEmpty()) 0L else 1L
) = PagedResult(
    items = items,
    totalCount = totalCount,
    pageNumber = pageNumber,
    pageSize = pageSize,
    totalPages = totalPages
)
