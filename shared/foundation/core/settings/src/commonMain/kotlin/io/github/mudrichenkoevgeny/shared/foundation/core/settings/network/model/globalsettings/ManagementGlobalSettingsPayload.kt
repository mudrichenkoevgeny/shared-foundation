package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Management global settings wire payload.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property maintenanceUntilEpochMillis Maintenance mode expiration timestamp in epoch milliseconds.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type string.
 * @property isTracingEnabled Global flag indicating whether request distributed tracing generation and export are enabled.
 * @property isMetricsEnabled Global flag indicating whether system and application performance metrics collection is enabled.
 * @property isVerboseLoggingEnabled Flag controlling logging verbosity; when false, the server disables detailed debug/info logs.
 */
@Serializable
data class ManagementGlobalSettingsPayload(
    @SerialName(GlobalSettingsApiFields.PRIVACY_POLICY_URL)
    val privacyPolicyUrl: String?,

    @SerialName(GlobalSettingsApiFields.TERMS_OF_SERVICE_URL)
    val termsOfServiceUrl: String?,

    @SerialName(GlobalSettingsApiFields.CONTACT_SUPPORT_EMAIL)
    val contactSupportEmail: String?,

    @SerialName(GlobalSettingsApiFields.MAINTENANCE_UNTIL_EPOCH_MILLIS)
    val maintenanceUntilEpochMillis: Long?,

    @SerialName(GlobalSettingsApiFields.MIN_SUPPORTED_APP_VERSIONS)
    val minSupportedAppVersions: Map<String, String>,

    @SerialName(GlobalSettingsApiFields.IS_TRACING_ENABLED)
    val isTracingEnabled: Boolean,

    @SerialName(GlobalSettingsApiFields.IS_METRICS_ENABLED)
    val isMetricsEnabled: Boolean,

    @SerialName(GlobalSettingsApiFields.IS_VERBOSE_LOGGING_ENABLED)
    val isVerboseLoggingEnabled: Boolean
)
