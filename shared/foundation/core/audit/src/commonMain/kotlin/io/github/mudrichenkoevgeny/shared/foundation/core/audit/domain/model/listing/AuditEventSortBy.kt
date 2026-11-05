package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for audit event list endpoints, aligned with fields on [AuditEventPrivate].
 *
 * @property value Wire value for `sort_by`.
 */
@Serializable
public enum class AuditEventSortBy(
    public val value: String
) {
    /** Sort by creation timestamp. */
    @SerialName(CommonApiFields.CREATED_AT)
    CREATED_AT(CommonApiFields.CREATED_AT);

    public companion object {
        /** Default sort field ([CREATED_AT]). */
        public val DEFAULT: AuditEventSortBy = CREATED_AT

        /**
         * Returns [AuditEventSortBy] for the given wire value or case-insensitive enum name.
         */
        public fun fromValueOrNull(value: String): AuditEventSortBy? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [AuditEventSortBy] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditEventSortBy].
         */
        public fun fromValueOrThrow(value: String): AuditEventSortBy =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${AuditEventSortBy::class.simpleName}: '$value'"
            )
    }
}
