package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STATUS_SUCCESS = "success"
private const val STATUS_FAILED = "failed"
private const val STATUS_DENIED = "denied"

/**
 * Outcome of an audited action on the API wire and in shared domain code.
 *
 * @property value String representation of the [AuditStatus].
 */
@Serializable
enum class AuditStatus(val value: String) {
    /** Action completed successfully. */
    @SerialName(STATUS_SUCCESS)
    SUCCESS(STATUS_SUCCESS),

    /** Action was attempted but failed (e.g. validation or business error). */
    @SerialName(STATUS_FAILED)
    FAILED(STATUS_FAILED),

    /** Action was denied (e.g. insufficient permissions). */
    @SerialName(STATUS_DENIED)
    DENIED(STATUS_DENIED);

    companion object {
        /**
         * Returns [AuditStatus] based on the provided wire value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditStatus? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [AuditStatus] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditStatus].
         */
        fun fromValueOrThrow(value: String): AuditStatus =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for AuditStatus: '$value'")
    }
}