package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Management global platform settings.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type.
 * @property isTracingEnabled Global flag indicating whether request distributed tracing generation and export are enabled.
 * @property isMetricsEnabled Global flag indicating whether system and application performance metrics collection is enabled.
 * @property isVerboseLoggingEnabled Flag controlling logging verbosity; when false, the server disables detailed debug/info logs.
 */
public data class ManagementGlobalSettings(
    public val privacyPolicyUrl: String?,
    public val termsOfServiceUrl: String?,
    public val contactSupportEmail: String?,
    public val minSupportedAppVersions: Map<ClientType, String>,
    public val isTracingEnabled: Boolean,
    public val isMetricsEnabled: Boolean,
    public val isVerboseLoggingEnabled: Boolean
)
