package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val TYPE_USER = "user"
private const val TYPE_SYSTEM = "system"
private const val TYPE_SERVICE = "service"

/**
 * Type of actor that initiated an audited action.
 *
 * @property value String representation of the [AuditActorType].
 */
@Serializable
enum class AuditActorType(val value: String) {
    /** Authenticated user actor. */
    @SerialName(TYPE_USER)
    USER(TYPE_USER),

    /** Internal system actor. */
    @SerialName(TYPE_SYSTEM)
    SYSTEM(TYPE_SYSTEM),

    /** External or internal service actor. */
    @SerialName(TYPE_SERVICE)
    SERVICE(TYPE_SERVICE);

    companion object {
        /**
         * Returns [AuditActorType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditActorType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [AuditActorType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditActorType].
         */
        fun fromValueOrThrow(value: String): AuditActorType = fromValueOrNull(value)
            ?: throw IllegalArgumentException("Unknown value for AuditActorType: '$value'")
    }
}