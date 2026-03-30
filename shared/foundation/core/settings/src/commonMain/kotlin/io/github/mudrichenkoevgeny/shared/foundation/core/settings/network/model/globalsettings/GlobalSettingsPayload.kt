package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Global public settings used by clients and management flows.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 */
@Serializable
data class GlobalSettingsPayload(
    @SerialName(GlobalSettingsApiFields.PRIVACY_POLICY_URL)
    val privacyPolicyUrl: String?,

    @SerialName(GlobalSettingsApiFields.TERMS_OF_SERVICE_URL)
    val termsOfServiceUrl: String?,

    @SerialName(GlobalSettingsApiFields.CONTACT_SUPPORT_EMAIL)
    val contactSupportEmail: String?
)