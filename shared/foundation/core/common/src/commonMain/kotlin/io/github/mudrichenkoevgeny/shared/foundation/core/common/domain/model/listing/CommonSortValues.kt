package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Shared **wire values** for list sorting (for example `sort_by` and `sort_order` query parameters or internal
 * representations that must match that contract).
 */
object CommonSortValues {
    /**
     * Supported `sort_by` values for timestamp-based fields.
     */
    @Serializable
    enum class TimestampSortBy {
        /** Sort by [CommonApiFields.CREATED_AT]. */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT,

        /** Sort by [CommonApiFields.UPDATED_AT]. */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT;

        /**
         * Wire value for `sort_by` (matches [CommonApiFields] timestamp field names).
         */
        val serialName: String
            get() = when (this) {
                CREATED_AT -> CommonApiFields.CREATED_AT
                UPDATED_AT -> CommonApiFields.UPDATED_AT
            }

        companion object {
            /**
             * Returns [TimestampSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): TimestampSortBy? =
                runCatching { valueOf(value.uppercase()) }.getOrNull()

            /**
             * Returns [TimestampSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [TimestampSortBy].
             */
            fun fromValueOrThrow(value: String): TimestampSortBy = valueOf(value.uppercase())
        }
    }
}
