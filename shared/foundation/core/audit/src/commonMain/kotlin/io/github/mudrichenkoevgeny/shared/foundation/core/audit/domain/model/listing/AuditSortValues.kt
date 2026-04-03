package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Supported `sort_by` wire values for audit event list endpoints (aligned with [AuditEventPayload.createdAt]).
 */
object AuditSortValues {
    /**
     * Supported `sort_by` values for audit event list endpoints.
     */
    @Serializable
    enum class AuditEventSortBy {
        /** Sort by creation timestamp ([CommonApiFields.CREATED_AT]; [AuditEventPayload.createdAt]). */
        @SerialName(CommonApiFields.CREATED_AT)
        CREATED_AT;

        /** Wire value for `sort_by`. */
        val serialName: String
            get() = CommonApiFields.CREATED_AT

        companion object {
            /**
             * Returns [AuditEventSortBy] for the given wire value or case-insensitive enum name.
             */
            fun fromValueOrNull(value: String): AuditEventSortBy? =
                runCatching { valueOf(value.uppercase()) }.getOrNull()

            /**
             * Returns [AuditEventSortBy] for the given wire or enum-style string.
             *
             * @throws IllegalArgumentException if [value] does not match any [AuditEventSortBy].
             */
            fun fromValueOrThrow(value: String): AuditEventSortBy = valueOf(value.uppercase())
        }
    }
}
