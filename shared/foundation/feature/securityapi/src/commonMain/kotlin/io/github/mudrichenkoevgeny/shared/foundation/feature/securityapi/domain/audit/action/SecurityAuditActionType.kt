package io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.management.security.settings.ManagementSecuritySettingsRoutes

/**
 * Security-module audit actions.
 */
enum class SecurityAuditActionType : AuditActionType {
    /** [ManagementSecuritySettingsRoutes.UPDATE_MANAGEMENT_SECURITY_SETTINGS]. */
    MANAGEMENT_UPDATE_SECURITY_SETTINGS,

    /** Reused rotated refresh token after grace period. */
    REFRESH_TOKEN_REUSE_DETECTED;

    /**
     * String representation of the [SecurityAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_SECURITY_SETTINGS -> ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS
            REFRESH_TOKEN_REUSE_DETECTED -> ACTION_REFRESH_TOKEN_REUSE_DETECTED
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        private const val ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS = "management_update_security_settings"
        private const val ACTION_REFRESH_TOKEN_REUSE_DETECTED = "refresh_token_reuse_detected"

        /**
         * Returns [SecurityAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SecurityAuditActionType? =
            entries.firstOrNull {
                it.serialName.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            } ?: runCatching {
                valueOf(value.uppercase())
            }.getOrNull()

        /**
         * Returns [SecurityAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditActionType].
         */
        fun fromValueOrThrow(value: String): SecurityAuditActionType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SecurityAuditActionType: '$value'")
    }
}