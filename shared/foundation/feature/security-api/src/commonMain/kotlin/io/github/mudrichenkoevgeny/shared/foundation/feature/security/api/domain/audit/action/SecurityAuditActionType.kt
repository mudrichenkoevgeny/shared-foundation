package io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.network.route.management.security.settings.ManagementSecuritySettingsRoutes

/**
 * Security-module audit actions.
 */
enum class SecurityAuditActionType : AuditActionType {
    /** [ManagementSecuritySettingsRoutes.UPDATE_SECURITY_SETTINGS]. */
    MANAGEMENT_UPDATE_SECURITY_SETTINGS;

    /**
     * String representation of the [SecurityAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_SECURITY_SETTINGS -> ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS = "management_update_security_settings"

        /**
         * Returns [SecurityAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SecurityAuditActionType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SecurityAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditActionType].
         */
        fun fromValueOrThrow(value: String): SecurityAuditActionType = valueOf(value.uppercase())
    }
}