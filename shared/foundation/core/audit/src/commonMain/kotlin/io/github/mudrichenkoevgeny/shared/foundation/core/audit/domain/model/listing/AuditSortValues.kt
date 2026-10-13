package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for audit event list endpoints (aligned with [AuditEventPrivatePayload.createdAt]).
 */
object AuditSortValues {
    /**
     * Supported `sort_by` values for audit event list endpoints.
     *
     * @property value Wire value for `sort_by`.
     */
    @Serializable
    enum class AuditEventSortBy(val value: String) {
        /** Sort by creation timestamp ([CommonApiFields.CREATED_AT]; [AuditEventPrivatePayload.createdAt]). */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT(CommonApiFields.CREATED_AT),

        /** Sort by update timestamp ([CommonApiFields.UPDATED_AT]; [AuditEventPrivatePayload.updatedAt]). */
        @SerialName(CommonApiFields.UPDATED_AT)
        UPDATED_AT(CommonApiFields.UPDATED_AT);

        companion object {
            /**
             * Default sort field ([CREATED_AT]).
             */
            val DEFAULT = CREATED_AT

            /**
             * Returns [AuditEventSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): AuditEventSortBy? =
                entries.firstOrNull {
                    it.value.equals(value, ignoreCase = true) ||
                            it.name.equals(value, ignoreCase = true)
                }

            /**
             * Returns [AuditEventSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [AuditEventSortBy].
             */
            fun fromValueOrThrow(value: String): AuditEventSortBy =
                fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for AuditEventSortBy: '$value'")
        }
    }
}