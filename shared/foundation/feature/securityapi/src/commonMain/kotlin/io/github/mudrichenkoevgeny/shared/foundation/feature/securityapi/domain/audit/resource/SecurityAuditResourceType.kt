package io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload

/**
 * Security-module audit resource kinds.
 */
enum class SecurityAuditResourceType : AuditResourceType {
    /** [io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.management.security.settings.ManagementSecuritySettingsRoutes.UPDATE_SECURITY_SETTINGS] request/response ([SecuritySettingsPayload]). */
    SECURITY_SETTINGS;

    /**
     * String representation of the [SecurityAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            SECURITY_SETTINGS -> SecurityAuditResourceType.RESOURCE_SECURITY_SETTINGS
        }

    override fun parseOrNull(value: String): AuditResourceType? =
        SecurityAuditResourceType.fromValueOrNull(
            value
        )

    override fun parseOrThrow(value: String): AuditResourceType =
        SecurityAuditResourceType.fromValueOrThrow(
            value
        )

    companion object {
        private const val RESOURCE_SECURITY_SETTINGS = "security_settings"

        /**
         * Returns [SecurityAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.resource.SecurityAuditResourceType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [SecurityAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditResourceType].
         */
        fun fromValueOrThrow(value: String): io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.resource.SecurityAuditResourceType = valueOf(value.uppercase())
    }
}
