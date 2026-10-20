package io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.domain.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings

/**
 * Creates a test [OpenGlobalSettings] domain instance.
 *
 * @param privacyPolicyUrl URL of the privacy policy.
 * @param termsOfServiceUrl URL of the terms of service.
 * @param contactSupportEmail Contact support email address.
 * @param minSupportedAppVersions Map of client types to minimum supported app versions.
 */
fun createTestOpenGlobalSettings(
    privacyPolicyUrl: String? = "https://example.com/privacy",
    termsOfServiceUrl: String? = "https://example.com/terms",
    contactSupportEmail: String? = "support@example.com",
    minSupportedAppVersions: Map<ClientType, String> = mapOf(ClientType.ANDROID to "1.0.0")
) = OpenGlobalSettings(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail,
    minSupportedAppVersions = minSupportedAppVersions
)
