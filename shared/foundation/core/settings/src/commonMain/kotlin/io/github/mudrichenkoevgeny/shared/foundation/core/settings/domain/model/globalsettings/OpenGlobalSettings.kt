package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType

/**
 * Open global platform settings.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 * @property maintenanceUntilEpochMillis Maintenance mode expiration timestamp in epoch milliseconds.
 * @property minSupportedAppVersions Minimum supported application versions mapped by client type.
 */
data class OpenGlobalSettings(
    val privacyPolicyUrl: String?,
    val termsOfServiceUrl: String?,
    val contactSupportEmail: String?,
    val maintenanceUntilEpochMillis: Long?,
    val minSupportedAppVersions: Map<ClientType, String>
)
