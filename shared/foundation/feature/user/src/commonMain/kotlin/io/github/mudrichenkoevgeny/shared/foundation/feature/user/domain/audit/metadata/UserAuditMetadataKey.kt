package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId

/**
 * User-feature audit metadata keys for [AuditEventMetadata].
 */
enum class UserAuditMetadataKey : AuditMetadataKey {
    /**
     * Target or acting user; [AuditEventMetadata.value] is a UUID hex-with-dashes string aligned with [UserId.asHexDashString].
     */
    USER_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * Authenticated session; [AuditEventMetadata.value] is a UUID hex-with-dashes string aligned with [UserSessionId.asHexDashString].
     */
    SESSION_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
    },

    /**
     * User identifier record; [AuditEventMetadata.value] is a UUID hex-with-dashes string aligned with [UserIdentifierId.asHexDashString].
     */
    IDENTIFIER_ID {
        override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.FULL_VALUE_MASK
    };

    override val serialName: String
        get() = when (this) {
            USER_ID -> KEY_USER_ID
            SESSION_ID -> KEY_SESSION_ID
            IDENTIFIER_ID -> KEY_IDENTIFIER_ID
        }

    override fun parseOrNull(value: String): AuditMetadataKey? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditMetadataKey = fromValueOrThrow(value)

    companion object {
        const val KEY_USER_ID = "user_id"
        const val KEY_SESSION_ID = "session_id"
        const val KEY_IDENTIFIER_ID = "identifier_id"

        /**
         * Returns [UserAuditMetadataKey] for a wire or enum-style string: first by case-insensitive enum constant name,
         * then by case-insensitive [serialName] match.
         */
        fun fromValueOrNull(value: String): AuditMetadataKey? {
            val byConstantName = runCatching { valueOf(value.uppercase()) }.getOrNull()
            if (byConstantName != null) {
                return byConstantName
            }

            return entries.firstOrNull { entry ->
                entry.serialName.equals(value, ignoreCase = true)
            }
        }

        /**
         * Returns [UserAuditMetadataKey] for the given wire or enum-style string.
         *
         * @throws IllegalStateException if [value] does not match any [UserAuditMetadataKey].
         */
        fun fromValueOrThrow(value: String): AuditMetadataKey =
            fromValueOrNull(value) ?: error("Unknown value of UserAuditMetadataKey: '$value'")
    }
}
