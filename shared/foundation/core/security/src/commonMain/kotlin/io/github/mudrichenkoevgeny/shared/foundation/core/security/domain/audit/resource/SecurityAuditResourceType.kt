package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val RESOURCE_SECURITY_SETTINGS = "security_settings"

/**
 * Security-module audit resource kinds.
 *
 * @property value String representation of the [SecurityAuditResourceType].
 */
@Serializable
enum class SecurityAuditResourceType(override val value: String) : AuditResourceType {
    /** Global security settings resource ([ManagementSecuritySettingsRoutes]). */
    @SerialName(RESOURCE_SECURITY_SETTINGS)
    SECURITY_SETTINGS(RESOURCE_SECURITY_SETTINGS);

    companion object {
        /**
         * Returns [SecurityAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SecurityAuditResourceType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [SecurityAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SecurityAuditResourceType].
         */
        fun fromValueOrThrow(value: String): SecurityAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SecurityAuditResourceType: '$value'")
    }
}
