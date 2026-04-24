package io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.network.route.management.globalsettings.ManagementGlobalSettingsRoutes

/**
 * Settings-module audit resource kinds (global settings, not security settings).
 */
enum class SettingsAuditResourceType : AuditResourceType {
    /** [ManagementGlobalSettingsRoutes.UPDATE_GLOBAL_SETTINGS] request/response payload. */
    GLOBAL_SETTINGS;

    /**
     * String representation of the [SettingsAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            GLOBAL_SETTINGS -> RESOURCE_GLOBAL_SETTINGS
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        private const val RESOURCE_GLOBAL_SETTINGS = "global_settings"

        /**
         * Returns [SettingsAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SettingsAuditResourceType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SettingsAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SettingsAuditResourceType].
         */
        fun fromValueOrThrow(value: String): SettingsAuditResourceType = valueOf(value.uppercase())
    }
}
