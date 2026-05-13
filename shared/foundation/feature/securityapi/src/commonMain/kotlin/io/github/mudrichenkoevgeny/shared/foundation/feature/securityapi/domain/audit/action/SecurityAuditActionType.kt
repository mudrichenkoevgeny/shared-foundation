package io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType

/**
 * Security-module audit actions.
 */
enum class SecurityAuditActionType : AuditActionType {
    /** [io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.management.security.settings.ManagementSecuritySettingsRoutes.UPDATE_SECURITY_SETTINGS]. */
    MANAGEMENT_UPDATE_SECURITY_SETTINGS;

    /**
     * String representation of the [SecurityAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_SECURITY_SETTINGS -> _root_ide_package_.io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType.Companion.ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS
        }

    override fun parseOrNull(value: String): AuditActionType? =
        _root_ide_package_.io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType.Companion.fromValueOrNull(
            value
        )

    override fun parseOrThrow(value: String): AuditActionType =
        _root_ide_package_.io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType.Companion.fromValueOrThrow(
            value
        )

    companion object {
        private const val ACTION_MANAGEMENT_UPDATE_SECURITY_SETTINGS = "management_update_security_settings"

        /**
         * Returns [SecurityAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SecurityAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditActionType].
         */
        fun fromValueOrThrow(value: String): io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType = valueOf(value.uppercase())
    }
}