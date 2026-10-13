package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sort direction for list endpoints. Serialized with the same strings as [CommonApiFields.SortOrder].
 *
 * @property value Wire value for `sort_order` (matches [CommonApiFields.SortOrder]).
 */
@Serializable
enum class SortOrder(val value: String) {
    /** Ascending order ([CommonApiFields.SortOrder.ASC]). */
    @SerialName(CommonApiFields.SortOrder.ASC)
    ASC(CommonApiFields.SortOrder.ASC),

    /** Descending order ([CommonApiFields.SortOrder.DESC]). */
    @SerialName(CommonApiFields.SortOrder.DESC)
    DESC(CommonApiFields.SortOrder.DESC);

    companion object {
        /**
         * Default sort direction ([DESC]).
         */
        val DEFAULT = DESC

        /**
         * Returns [SortOrder] for the given wire value (`asc` / `desc`) or case-insensitive enum name (`ASC` / `DESC`).
         */
        fun fromValueOrNull(value: String): SortOrder? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [SortOrder] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [SortOrder].
         */
        fun fromValueOrThrow(value: String): SortOrder =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SortOrder: '$value'")
    }
}