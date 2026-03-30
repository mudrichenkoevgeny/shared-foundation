package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * Shared **wire values** for list sorting (for example `sort_by` and `sort_order` query parameters or internal
 * representations that must match that contract).
 */
object CommonSortValues {
    /**
     * Supported `sort_by` values for timestamp-based fields.
     */
    object TimestampSortBy {
        /** Sort by [CommonApiFields.CREATED_AT]. */
        const val CREATED_AT = CommonApiFields.CREATED_AT

        /** Sort by [CommonApiFields.UPDATED_AT]. */
        const val UPDATED_AT = CommonApiFields.UPDATED_AT
    }

    /**
     * Supported ordering direction values for list endpoints (same strings as [CommonApiFields.SortOrder]).
     */
    object SortOrder {
        /** Ascending order ([CommonApiFields.SortOrder.ASC]). */
        const val ASC = CommonApiFields.SortOrder.ASC

        /** Descending order ([CommonApiFields.SortOrder.DESC]). */
        const val DESC = CommonApiFields.SortOrder.DESC
    }
}
