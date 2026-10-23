package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS = "management_update_security_settings"
private const val ACTION_MANAGEMENT_RESET_SECURITY_SETTINGS = "management_reset_security_settings"

/**
 * Security-module audit action kinds for platform-wide security configuration changes.
 *
 * @property value String representation of the [SecurityAuditActionType].
 */
@Serializable
enum class SecurityAuditActionType(override val value: String) : AuditActionType {
    /** Administrative update of platform-wide security policy settings via [ManagementSecuritySettingsRoutes.UPDATE_MANAGEMENT_SECURITY_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS)
    MANAGEMENT_UPDATE_SECURITY_SETTINGS(ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS),

    /** Administrative reset of security policy settings to default platform values via [ManagementSecuritySettingsRoutes.RESET_MANAGEMENT_SECURITY_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_RESET_SECURITY_SETTINGS)
    MANAGEMENT_RESET_SECURITY_SETTINGS(ACTION_MANAGEMENT_RESET_SECURITY_SETTINGS);

    companion object {
        /**
         * Returns [SecurityAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SecurityAuditActionType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [SecurityAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditActionType].
         */
        fun fromValueOrThrow(value: String): SecurityAuditActionType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SecurityAuditActionType: '$value'")
    }
}
