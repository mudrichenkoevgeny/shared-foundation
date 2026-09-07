package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Open global settings wire payload.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property maintenanceUntilEpochMillis Maintenance mode expiration timestamp in epoch milliseconds.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type string.
 */
@Serializable
data class OpenGlobalSettingsPayload(
    @SerialName(GlobalSettingsApiFields.PRIVACY_POLICY_URL)
    val privacyPolicyUrl: String?,

    @SerialName(GlobalSettingsApiFields.TERMS_OF_SERVICE_URL)
    val termsOfServiceUrl: String?,

    @SerialName(GlobalSettingsApiFields.CONTACT_SUPPORT_EMAIL)
    val contactSupportEmail: String?,

    @SerialName(GlobalSettingsApiFields.MAINTENANCE_UNTIL_EPOCH_MILLIS)
    val maintenanceUntilEpochMillis: Long?,

    @SerialName(GlobalSettingsApiFields.MIN_SUPPORTED_APP_VERSIONS)
    val minSupportedAppVersions: Map<String, String>
)
