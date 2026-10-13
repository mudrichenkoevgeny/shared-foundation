package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SENSITIVITY_NON_SENSITIVE = "non_sensitive"
private const val SENSITIVITY_EMAIL = "email"
private const val SENSITIVITY_PHONE_NUMBER = "phone_number"
private const val SENSITIVITY_IP_ADDRESS = "ip_address"
private const val SENSITIVITY_PARTIAL_VALUE_MASK = "partial_value_mask"
private const val SENSITIVITY_FULL_VALUE_MASK = "full_value_mask"

/**
 * How a metadata entry [AuditEventMetadata.value] should be treated when shown in logs, admin UIs, or exports.
 *
 * @property value String representation of the [AuditValueSensitivity] for API wire values (snake_case).
 */
@Serializable
enum class AuditValueSensitivity(val value: String) {
    /**
     * Value is not sensitive; consumers may show or persist it as provided.
     */
    @SerialName(SENSITIVITY_NON_SENSITIVE)
    NON_SENSITIVE(SENSITIVITY_NON_SENSITIVE),

    /**
     * Email-shaped value; consumer may apply format-aware redaction.
     */
    @SerialName(SENSITIVITY_EMAIL)
    EMAIL(SENSITIVITY_EMAIL),

    /**
     * Phone-shaped value; consumer may apply format-aware redaction.
     */
    @SerialName(SENSITIVITY_PHONE_NUMBER)
    PHONE_NUMBER(SENSITIVITY_PHONE_NUMBER),

    /**
     * IP address-shaped value; consumer may apply format-aware redaction.
     */
    @SerialName(SENSITIVITY_IP_ADDRESS)
    IP_ADDRESS(SENSITIVITY_IP_ADDRESS),

    /**
     * Less sensitive: consumers may expose short prefix and/or suffix for support or correlation, masking the rest (e.g.
     * keep first and last few characters). Exact visible character counts are a consumer policy, not defined here.
     */
    @SerialName(SENSITIVITY_PARTIAL_VALUE_MASK)
    PARTIAL_VALUE_MASK(SENSITIVITY_PARTIAL_VALUE_MASK),

    /**
     * Highly sensitive: the entire raw value must not be revealed. Consumers should replace the whole string when
     * displaying or serializing for non-privileged contexts (e.g. a fixed token such as `****` or a constant length of
     * masking characters).
     */
    @SerialName(SENSITIVITY_FULL_VALUE_MASK)
    FULL_VALUE_MASK(SENSITIVITY_FULL_VALUE_MASK);

    companion object {
        /**
         * Returns [AuditValueSensitivity] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditValueSensitivity? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [AuditValueSensitivity] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditValueSensitivity].
         */
        fun fromValueOrThrow(value: String): AuditValueSensitivity =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for AuditValueSensitivity: '$value'")
    }
}