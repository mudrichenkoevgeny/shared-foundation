package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor

/**
 * Type of actor that initiated an audited action.
 */
enum class AuditActorType {
    /** Authenticated user actor. */
    USER,

    /** Internal system actor. */
    SYSTEM,

    /** External or internal service actor. */
    SERVICE;

    /**
     * String representation of the [AuditActorType].
     */
    val serialName: String
        get() = when (this) {
            USER -> TYPE_USER
            SYSTEM -> TYPE_SYSTEM
            SERVICE -> TYPE_SERVICE
        }

    companion object {
        private const val TYPE_USER = "user"
        private const val TYPE_SYSTEM = "system"
        private const val TYPE_SERVICE = "service"

        /**
         * Returns [AuditActorType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditActorType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [AuditActorType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditActorType].
         */
        fun fromValueOrThrow(value: String): AuditActorType = valueOf(value.uppercase())
    }
}