package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ACTION_MANAGEMENT_UPDATE_GLOBAL_SETTINGS = "management_update_global_settings"
private const val ACTION_MANAGEMENT_RESET_GLOBAL_SETTINGS = "management_reset_global_settings"

/**
 * Settings-module audit action kinds for platform-wide global configuration changes.
 *
 * @property value String representation of the [SettingsAuditActionType].
 */
@Serializable
enum class SettingsAuditActionType(override val value: String) : AuditActionType {
    /** Administrative update of platform-wide global configuration settings via [ManagementGlobalSettingsRoutes.UPDATE_MANAGEMENT_GLOBAL_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_UPDATE_GLOBAL_SETTINGS)
    MANAGEMENT_UPDATE_GLOBAL_SETTINGS(ACTION_MANAGEMENT_UPDATE_GLOBAL_SETTINGS),

    /** Administrative reset of global settings to platform default values via [ManagementGlobalSettingsRoutes.RESET_MANAGEMENT_GLOBAL_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_RESET_GLOBAL_SETTINGS)
    MANAGEMENT_RESET_GLOBAL_SETTINGS(ACTION_MANAGEMENT_RESET_GLOBAL_SETTINGS);

    companion object {
        /**
         * Returns [SettingsAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SettingsAuditActionType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [SettingsAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SettingsAuditActionType].
         */
        fun fromValueOrThrow(value: String): SettingsAuditActionType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SettingsAuditActionType: '$value'")
    }
}
