package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val KEY_USER_ID = "user_id"
private const val KEY_SESSION_ID = "session_id"
private const val KEY_IDENTIFIER_ID = "identifier_id"
private const val KEY_EMAIL_ADDRESS = "email_address"
private const val KEY_PHONE_NUMBER = "phone_number"
private const val KEY_USER_AUTH_PROVIDER = "user_auth_provider"
private const val KEY_EXTERNAL_ID = "external_id"
private const val KEY_ACCOUNT_LOCKOUT_TYPE = "account_lockout_type"
private const val KEY_TEMPORARY_LOCKOUT_UNTIL = "temporary_lockout_until"

/**
 * User-feature metadata key definitions for audit logs recorded in [AuditEventMetadata].
 *
 * @property value String representation of the [UserAuditMetadataKey].
 * @property valueSensitivity Sensitivity level for audit logging.
 */
@Serializable
enum class UserAuditMetadataKey(
    override val value: String,
    override val valueSensitivity: AuditValueSensitivity
) : AuditMetadataKey {
    /** Target or acting user account identifier aligned with [UserId.asHexDashString]. */
    @SerialName(KEY_USER_ID)
    USER_ID(KEY_USER_ID, AuditValueSensitivity.NON_SENSITIVE),

    /** Active session identifier aligned with [UserSessionId.asHexDashString]. */
    @SerialName(KEY_SESSION_ID)
    SESSION_ID(KEY_SESSION_ID, AuditValueSensitivity.NON_SENSITIVE),

    /** User identifier credential record aligned with [UserIdentifierId.asHexDashString]. */
    @SerialName(KEY_IDENTIFIER_ID)
    IDENTIFIER_ID(KEY_IDENTIFIER_ID, AuditValueSensitivity.NON_SENSITIVE),

    /** Email address associated with the user account or audit event. */
    @SerialName(KEY_EMAIL_ADDRESS)
    EMAIL_ADDRESS(KEY_EMAIL_ADDRESS, AuditValueSensitivity.EMAIL),

    /** Phone number associated with the user account or authentication event. */
    @SerialName(KEY_PHONE_NUMBER)
    PHONE_NUMBER(KEY_PHONE_NUMBER, AuditValueSensitivity.PHONE_NUMBER),

    /** External OAuth provider or identity context string. */
    @SerialName(KEY_USER_AUTH_PROVIDER)
    USER_AUTH_PROVIDER(KEY_USER_AUTH_PROVIDER, AuditValueSensitivity.NON_SENSITIVE),

    /** External subject identifier returned by third-party OAuth providers. */
    @SerialName(KEY_EXTERNAL_ID)
    EXTERNAL_ID(KEY_EXTERNAL_ID, AuditValueSensitivity.PARTIAL_VALUE_MASK),

    /** Account lockout enforcement type aligned with [AccountLockoutType.value]. */
    @SerialName(KEY_ACCOUNT_LOCKOUT_TYPE)
    ACCOUNT_LOCKOUT_TYPE(KEY_ACCOUNT_LOCKOUT_TYPE, AuditValueSensitivity.NON_SENSITIVE),

    /** Expiration timestamp for temporary account lockouts in Unix epoch milliseconds. */
    @SerialName(KEY_TEMPORARY_LOCKOUT_UNTIL)
    TEMPORARY_LOCKOUT_UNTIL(KEY_TEMPORARY_LOCKOUT_UNTIL, AuditValueSensitivity.NON_SENSITIVE);

    companion object {
        /**
         * Returns [UserAuditMetadataKey] for a wire or enum-style string: first by case-insensitive enum constant name,
         * then by case-insensitive [value] match.
         */
        fun fromValueOrNull(value: String): UserAuditMetadataKey? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserAuditMetadataKey] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuditMetadataKey].
         */
        fun fromValueOrThrow(value: String): UserAuditMetadataKey =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserAuditMetadataKey: '$value'")
    }
}
