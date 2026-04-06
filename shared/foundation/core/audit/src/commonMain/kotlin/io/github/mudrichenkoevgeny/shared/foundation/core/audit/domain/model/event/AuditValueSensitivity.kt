package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata

/**
 * How a metadata entry [AuditEventMetadata.value] should be treated when shown in logs, admin UIs, or exports.
 */
enum class AuditValueSensitivity {
    /**
     * Value is not sensitive; consumers may show or persist it as provided.
     */
    NON_SENSITIVE,

    /**
     * Email-shaped value; consumer may apply format-aware redaction.
     */
    EMAIL,

    /**
     * Phone-shaped value; consumer may apply format-aware redaction.
     */
    PHONE_NUMBER,

    /**
     * IP address-shaped value; consumer may apply format-aware redaction.
     */
    IP_ADDRESS,

    /**
     * Less sensitive: consumers may expose short prefix and/or suffix for support or correlation, masking the rest (e.g.
     * keep first and last few characters). Exact visible character counts are a consumer policy, not defined here.
     */
    PARTIAL_VALUE_MASK,

    /**
     * Highly sensitive: the entire raw value must not be revealed. Consumers should replace the whole string when
     * displaying or serializing for non-privileged contexts (e.g. a fixed token such as `****` or a constant length of
     * masking characters).
     */
    FULL_VALUE_MASK;

    /**
     * String representation of the [AuditValueSensitivity] for API wire values (snake_case).
     */
    val serialName: String
        get() = when (this) {
            NON_SENSITIVE -> SENSITIVITY_NON_SENSITIVE
            EMAIL -> SENSITIVITY_EMAIL
            PHONE_NUMBER -> SENSITIVITY_PHONE_NUMBER
            IP_ADDRESS -> SENSITIVITY_IP_ADDRESS
            PARTIAL_VALUE_MASK -> SENSITIVITY_PARTIAL_VALUE_MASK
            FULL_VALUE_MASK -> SENSITIVITY_FULL_VALUE_MASK
        }

    companion object {
        const val SENSITIVITY_NON_SENSITIVE = "non_sensitive"
        const val SENSITIVITY_EMAIL = "email"
        const val SENSITIVITY_PHONE_NUMBER = "phone_number"
        const val SENSITIVITY_IP_ADDRESS = "ip_address"
        const val SENSITIVITY_PARTIAL_VALUE_MASK = "partial_value_mask"
        const val SENSITIVITY_FULL_VALUE_MASK = "full_value_mask"

        /**
         * Returns [AuditValueSensitivity] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): AuditValueSensitivity? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [AuditValueSensitivity] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [AuditValueSensitivity].
         */
        fun fromValueOrThrow(value: String): AuditValueSensitivity =
            valueOf(value.uppercase())
    }
}
