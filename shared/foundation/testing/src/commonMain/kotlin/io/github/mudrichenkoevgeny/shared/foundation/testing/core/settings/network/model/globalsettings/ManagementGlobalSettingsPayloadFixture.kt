package io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.network.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload

/**
 * Creates a test [ManagementGlobalSettingsPayload] wire object instance.
 *
 * @param privacyPolicyUrl URL of the privacy policy.
 * @param termsOfServiceUrl URL of the terms of service.
 * @param contactSupportEmail Contact support email address.
 * @param minSupportedAppVersions Map of client type wire values to minimum supported app versions.
 * @param isTracingEnabled Flag indicating whether tracing is enabled.
 * @param isMetricsEnabled Flag indicating whether metrics collection is enabled.
 * @param isVerboseLoggingEnabled Flag indicating whether verbose logging is enabled.
 */
fun createTestManagementGlobalSettingsPayload(
    privacyPolicyUrl: String? = "https://example.com/privacy",
    termsOfServiceUrl: String? = "https://example.com/terms",
    contactSupportEmail: String? = "support@example.com",
    minSupportedAppVersions: Map<String, String> = mapOf(ClientType.ANDROID.value to "1.0.0"),
    isTracingEnabled: Boolean = true,
    isMetricsEnabled: Boolean = true,
    isVerboseLoggingEnabled: Boolean = false
) = ManagementGlobalSettingsPayload(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail,
    minSupportedAppVersions = minSupportedAppVersions,
    isTracingEnabled = isTracingEnabled,
    isMetricsEnabled = isMetricsEnabled,
    isVerboseLoggingEnabled = isVerboseLoggingEnabled
)
