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
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type string.
 */
@Serializable
public data class OpenGlobalSettingsPayload(
    @SerialName(GlobalSettingsApiFields.PRIVACY_POLICY_URL)
    public val privacyPolicyUrl: String?,
    @SerialName(GlobalSettingsApiFields.TERMS_OF_SERVICE_URL)
    public val termsOfServiceUrl: String?,
    @SerialName(GlobalSettingsApiFields.CONTACT_SUPPORT_EMAIL)
    public val contactSupportEmail: String?,
    @SerialName(GlobalSettingsApiFields.MIN_SUPPORTED_APP_VERSIONS)
    public val minSupportedAppVersions: Map<String, String>
)
