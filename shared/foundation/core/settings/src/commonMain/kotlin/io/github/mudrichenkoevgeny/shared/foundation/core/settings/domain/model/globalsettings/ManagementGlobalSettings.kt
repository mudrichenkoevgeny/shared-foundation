package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Management global platform settings.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property maintenanceUntilEpochMillis Maintenance mode expiration timestamp in epoch milliseconds.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type.
 * @property isTracingEnabled Global flag indicating whether request distributed tracing generation and export are enabled.
 * @property isMetricsEnabled Global flag indicating whether system and application performance metrics collection is enabled.
 * @property isVerboseLoggingEnabled Flag controlling logging verbosity; when false, the server disables detailed debug/info logs.
 */
data class ManagementGlobalSettings(
    val privacyPolicyUrl: String?,
    val termsOfServiceUrl: String?,
    val contactSupportEmail: String?,
    val maintenanceUntilEpochMillis: Long?,
    val minSupportedAppVersions: Map<ClientType, String>,
    val isTracingEnabled: Boolean,
    val isMetricsEnabled: Boolean,
    val isVerboseLoggingEnabled: Boolean
)
