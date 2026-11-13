package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` values for common timestamp-based fields.
 *
 * @property value Wire value for `sort_by` (matches [CommonApiFields] timestamp field names).
 */
@Serializable
public enum class CommonSortValues(
    public val value: String
) {
    /** Sort by [CommonApiFields.CREATED_AT]. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT),

    /** Sort by [CommonApiFields.UPDATED_AT]. */
    @SerialName(CommonApiFields.UPDATED_AT)
    UPDATED_AT(CommonApiFields.UPDATED_AT);

    public companion object {
        /** Default sort field ([CREATED_AT]). */
        public val DEFAULT: CommonSortValues = CREATED_AT

        /**
         * Returns [CommonSortValues] for the given wire value or case-insensitive enum name.
         */
        public fun fromValueOrNull(value: String): CommonSortValues? = entries.firstOrNull {
            it.value.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true)
        }

        /**
         * Returns [CommonSortValues] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [CommonSortValues].
         */
        public fun fromValueOrThrow(value: String): CommonSortValues =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${CommonSortValues::class.simpleName}: '$value'"
            )
    }
}
