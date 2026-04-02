package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sort direction for list endpoints. Serialized with the same strings as [CommonApiFields.SortOrder].
 */
@Serializable
enum class SortOrder {
    /** Ascending order ([CommonApiFields.SortOrder.ASC]). */
    @SerialName(CommonApiFields.SortOrder.ASC)
    ASC,

    /** Descending order ([CommonApiFields.SortOrder.DESC]). */
    @SerialName(CommonApiFields.SortOrder.DESC)
    DESC;

    /**
     * Wire value for `sort_order` (matches [CommonApiFields.SortOrder]).
     */
    val serialName: String
        get() = when (this) {
            ASC -> CommonApiFields.SortOrder.ASC
            DESC -> CommonApiFields.SortOrder.DESC
        }

    companion object {
        /**
         * Returns [SortOrder] for the given wire value (`asc` / `desc`) or case-insensitive enum name (`ASC` / `DESC`).
         */
        fun fromValueOrNull(value: String): SortOrder? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SortOrder] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [SortOrder].
         */
        fun fromValueOrThrow(value: String): SortOrder = valueOf(value.uppercase())
    }
}
