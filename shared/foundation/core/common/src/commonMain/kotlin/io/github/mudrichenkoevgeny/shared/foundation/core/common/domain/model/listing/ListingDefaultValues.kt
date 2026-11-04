package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

/**
 * Default fallback values for paginated listings shared across the system.
 */
object ListingDefaultValues {

    /**
     * Default pagination values.
     */
    object Pagination {
        /** Default one-based page index (`1`). */
        const val INITIAL_PAGE_NUMBER = 1

        /** Default maximum number of items returned per page (`20`). */
        const val DEFAULT_PAGE_SIZE = 20

        /** Extended page size for batch or large listing queries (`100`). */
        const val LARGE_PAGE_SIZE = 100
    }
}
