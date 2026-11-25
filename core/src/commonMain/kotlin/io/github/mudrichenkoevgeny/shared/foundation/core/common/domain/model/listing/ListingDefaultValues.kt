package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

/**
 * Default fallback values for paginated listings shared across the system.
 */
public object ListingDefaultValues {
    /**
     * Default pagination values.
     */
    public object Pagination {
        /** Default one-based page index (`1`). */
        public const val INITIAL_PAGE_NUMBER: Int = 1

        /** Default maximum number of items returned per page (`20`). */
        public const val DEFAULT_PAGE_SIZE: Int = 20

        /** Extended page size for batch or large listing queries (`100`). */
        public const val LARGE_PAGE_SIZE: Int = 100
    }
}
