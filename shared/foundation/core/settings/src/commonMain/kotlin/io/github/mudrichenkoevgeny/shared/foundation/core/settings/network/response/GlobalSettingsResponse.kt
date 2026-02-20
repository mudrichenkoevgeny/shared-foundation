package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.response

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the response with global settings.
 *
 * @property privacyPolicyUrl The legal URL pointing to the privacy policy.
 * @property termsOfServiceUrl The legal URL pointing to the terms of service.
 * @property contactSupportEmail The official email address for user support inquiries.
 */
@Serializable
data class  GlobalSettingsResponse(
    @SerialName(GlobalSettingsApiFields.PRIVACY_POLICY_URL)
    val privacyPolicyUrl: String?,

    @SerialName(GlobalSettingsApiFields.TERMS_OF_SERVICE_URL)
    val termsOfServiceUrl: String?,

    @SerialName(GlobalSettingsApiFields.CONTACT_SUPPORT_EMAIL)
    val contactSupportEmail: String?
)