package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status

/**
 * Outcome of an audited action on the API wire and in shared domain code.
 */
enum class AuditStatus {
    /** Action completed successfully. */
    SUCCESS,

    /** Action was attempted but failed (e.g. validation or business error). */
    FAILED,

    /** Action was denied (e.g. insufficient permissions). */
    DENIED;

    /**
     * String representation of the [AuditStatus].
     */
    val serialName: String
        get() = when (this) {
            SUCCESS -> STATUS_SUCCESS
            FAILED -> STATUS_FAILED
            DENIED -> STATUS_DENIED
        }

    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val STATUS_FAILED = "failed"
        private const val STATUS_DENIED = "denied"

        /**
         * Returns [AuditStatus] based on the provided wire value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditStatus? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [AuditStatus] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditStatus].
         */
        fun fromValueOrThrow(value: String): AuditStatus = valueOf(value.uppercase())
    }
}