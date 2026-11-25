package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

/**
 * Common baseline filter query parameter keys shared across resource list endpoints.
 *
 * Range filters (`*_from` and `*_to`) use **inclusive** bounds semantics:
 * - `*_from`: Includes items created or updated on or after the specified timestamp (inclusive lower bound).
 * - `*_to`: Includes items created or updated on or before the specified timestamp (inclusive upper bound).
 */
public object CommonFilterValues {
    /**
     * Creation timestamp inclusive lower bound filter key (`created_at_from`).
     * Includes items created on or after the specified Unix epoch millisecond timestamp.
     */
    public const val CREATED_AT_FROM: String = "created_at_from"

    /**
     * Creation timestamp inclusive upper bound filter key (`created_at_to`).
     * Includes items created on or before the specified Unix epoch millisecond timestamp.
     */
    public const val CREATED_AT_TO: String = "created_at_to"

    /**
     * Modification timestamp inclusive lower bound filter key (`updated_at_from`).
     * Includes items last modified on or after the specified Unix epoch millisecond timestamp.
     */
    public const val UPDATED_AT_FROM: String = "updated_at_from"

    /**
     * Modification timestamp inclusive upper bound filter key (`updated_at_to`).
     * Includes items last modified on or before the specified Unix epoch millisecond timestamp.
     */
    public const val UPDATED_AT_TO: String = "updated_at_to"
}
