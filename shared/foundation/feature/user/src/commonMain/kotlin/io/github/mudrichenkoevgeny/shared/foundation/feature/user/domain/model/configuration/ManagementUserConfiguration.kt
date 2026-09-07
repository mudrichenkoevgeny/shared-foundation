package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload

/**
 * Aggregated management configuration surfaced to staff and admin workflows.
 *
 * Wire shape for management configuration fetches: [ManagementUserConfigurationPayload].
 *
 * @property managementGlobalSettings Management global settings.
 * @property managementSecuritySettings Management security settings.
 * @property managementAuthSettings Management authentication settings.
 */
data class ManagementUserConfiguration(
    val managementGlobalSettings: ManagementGlobalSettings,
    val managementSecuritySettings: ManagementSecuritySettings,
    val managementAuthSettings: ManagementAuthSettings
)
