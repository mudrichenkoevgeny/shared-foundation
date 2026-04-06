package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.network.route.management.globalsettings.ManagementGlobalSettingsRoutes

/**
 * Settings-module audit actions (global settings, not security settings).
 */
enum class SettingsAuditActionType : AuditActionType {
    /** [ManagementGlobalSettingsRoutes.UPDATE_GLOBAL_SETTINGS]. */
    MANAGEMENT_UPDATE_GLOBAL_SETTINGS;

    /**
     * String representation of the [SettingsAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_GLOBAL_SETTINGS -> ACTION_MANAGEMENT_UPDATE_GLOBAL_SETTINGS
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_UPDATE_GLOBAL_SETTINGS = "management_update_global_settings"

        /**
         * Returns [SettingsAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SettingsAuditActionType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SettingsAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SettingsAuditActionType].
         */
        fun fromValueOrThrow(value: String): SettingsAuditActionType = valueOf(value.uppercase())
    }
}
