package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Open global platform settings.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type.
 */
public data class OpenGlobalSettings(
    public val privacyPolicyUrl: String?,
    public val termsOfServiceUrl: String?,
    public val contactSupportEmail: String?,
    public val minSupportedAppVersions: Map<ClientType, String>
)
